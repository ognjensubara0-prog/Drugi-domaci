package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@ApplicationScoped
public class BranchService {

    @Inject
    EntityManager em; 

    private static final String UPLOAD_DIR = "C:/RentACarFiles/";

    @Transactional
    public Branch addBranch(Branch branch) {
        if (branch.getReservations() != null) {
            branch.getReservations().forEach(r -> r.setBranch(branch));
        }
        return em.merge(branch);
    }

    public List<Branch> getAllBranches() {
        return em.createNamedQuery(Branch.GET_ALL_BRANCHES, Branch.class).getResultList();
    }

    public Branch getBranchById(Long id) {
        return em.find(Branch.class, id);
    }

    public List<Branch> findByCity(String city) {
        return em.createQuery("SELECT b FROM Branch b WHERE b.city = :city", Branch.class)
                 .setParameter("city", city)
                 .getResultList();
    }

    @Transactional
    public Branch uploadFileToBranch(Long branchId, FileUploadForm form) throws Exception {
        Branch branch = em.find(Branch.class, branchId);
        if (branch == null) {
            return null;
        }

        File uploadFolder = new File(UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String finalFileName = form.getFileName();
        Path targetPath = Paths.get(UPLOAD_DIR, finalFileName);
        UploadedFile uploadedFileEntity;

        
        List<UploadedFile> existingFiles = em.createQuery(
                "SELECT f FROM UploadedFile f WHERE f.filename = :path", UploadedFile.class)
                .setParameter("path", targetPath.toString())
                .getResultList();

        if (!existingFiles.isEmpty()) {
           
            uploadedFileEntity = existingFiles.get(0);
        } else {
            
            if (!Files.exists(targetPath)) {
                Path tempPath = form.getFile().uploadedFile();
                Files.copy(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            
            uploadedFileEntity = new UploadedFile(targetPath.toString());
            em.persist(uploadedFileEntity);
        }

        branch.getUploadedFiles().add(uploadedFileEntity);
        return em.merge(branch);
    }

    public Branch getBranchWithFilesLoaded(Long id) {
        Branch branch = em.find(Branch.class, id);
        if (branch == null) {
            return null;
        }
        
        for (UploadedFile uploadedFile : branch.getUploadedFiles()) {
            if (uploadedFile.getFilename() != null) {
                File physicalFile = new File(uploadedFile.getFilename());
                uploadedFile.setFile(physicalFile); 
            }
        }
        return branch;
    }
}