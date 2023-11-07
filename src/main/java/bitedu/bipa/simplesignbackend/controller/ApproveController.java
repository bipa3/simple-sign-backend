package bitedu.bipa.simplesignbackend.controller;

import bitedu.bipa.simplesignbackend.interceptor.Authority;
import bitedu.bipa.simplesignbackend.model.dto.*;
import bitedu.bipa.simplesignbackend.service.ApproveService;
import bitedu.bipa.simplesignbackend.service.S3Service;
import bitedu.bipa.simplesignbackend.service.UserService;
import bitedu.bipa.simplesignbackend.utils.FileCommonUtils;
import bitedu.bipa.simplesignbackend.utils.SessionUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/approve")
public class ApproveController {

    private final ApproveService approveService;
    private final S3Service s3Service;

    public ApproveController(ApproveService approveService, S3Service s3Service) {
        this.approveService = approveService;
        this.s3Service = s3Service;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping(value = "/register" )
    public ResponseEntity<String> approveRegister(@Valid @RequestPart ApprovalDocReqDTO approvalDocReqDTO,
                                                  @RequestPart(required = false) List<MultipartFile> files
                                                  ) throws IOException {
        //System.out.println(files.get(0).getOriginalFilename());
        int approvalDocId = approveService.registerApprovalDoc(approvalDocReqDTO);
        if (files != null ) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String uniqueFileName = s3Service.makeUniqueFileName(file, "approvalDoc");
                String s3Url = s3Service.upload(file, uniqueFileName);
                approveService.insertApprovalAttachment(s3Url, fileName, approvalDocId, uniqueFileName);
            }
        }
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/approval/{num}")
    public ResponseEntity<String> approveApprovalDoc(@PathVariable("num") int approvalDocId) {
        approveService.approveApprovalDoc(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/return/{num}")
    public ResponseEntity<String> returnApprovalDoc(@PathVariable("num") int approvalDocId) {
        approveService.returnApprovalDoc(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/detail/{num}")
    public ResponseEntity<ApprovalDocDetailDTO> showDetailApprovalDoc(@PathVariable("num") int approvalDocId) {
        ApprovalDocDetailDTO approvalDocDetailDTO =  approveService.showDetailApprovalDoc(approvalDocId);
        return ResponseEntity.ok(approvalDocDetailDTO);

    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PatchMapping("/{num}")
    public ResponseEntity<String>  updateApporvalDoc(@PathVariable("num") int approvalDocId, @RequestBody ApprovalDocReqDTO approvalDocReqDTO) {
        approveService.updateApprovalDoc(approvalDocId, approvalDocReqDTO);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @DeleteMapping("/{num}")
    public ResponseEntity<String> removeApprovalDoc(@PathVariable("num") int approvalDocId) {
        approveService.removeApprovalDoc(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/permissionList/{num}")
    public ResponseEntity<Boolean> getPermissionList(@PathVariable("num") int approvalDocId){
        boolean hasPermission =  approveService.hasPermission(approvalDocId);
        return ResponseEntity.ok(hasPermission);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/hasApproval/{num}")
    public ResponseEntity<Boolean> getHasApproval(@PathVariable("num") int approvalDocId){
        boolean hasApproval = approveService.getHasApproval(approvalDocId);
        return ResponseEntity.ok(hasApproval);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/hasUpdate/{num}")
    public ResponseEntity<Boolean> getHasUpdate(@PathVariable("num") int approvalDocId){
        boolean hasUpdate = approveService.getHasUpdate(approvalDocId);
        return ResponseEntity.ok(hasUpdate);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/hasDelete/{num}")
    public ResponseEntity<Boolean> getHasDelete(@PathVariable("num") int approvalDocId){
        boolean hasDelete = approveService.getHasDelete(approvalDocId);
        return ResponseEntity.ok(hasDelete);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/cancel/{num}")
    public ResponseEntity<String> cancelApproval(@PathVariable("num") int approvalDocId) {
        approveService.cancelApproval(approvalDocId);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PatchMapping("/temp/{num}")
    public ResponseEntity<String>  updateTemporalApprovalDoc(@PathVariable("num") int approvalDocId, @RequestBody ApprovalDocReqDTO approvalDocReqDTO) {
        approveService.updateTemporalApprovalDoc(approvalDocId, approvalDocReqDTO);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/password")
    public ResponseEntity<String> validPassword(@RequestBody Map<String,String> map) {
        //System.out.println("password: " + map.get("password"));
        approveService.validPassword(map.get("password"));
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/defaultApprovalLine/{num}")
    public ResponseEntity<List<ApprovalLineDetailListDTO>> getDefaultApprovalLine(@PathVariable("num") int formCode) {
        List<ApprovalLineDetailListDTO> approvalLineDetailListDTOList = approveService.getDefaultApprovalLine(formCode);
        return ResponseEntity.ok(approvalLineDetailListDTOList);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/favorites/{num}")
    public ResponseEntity<String> registerFavorites(@PathVariable("num") int formCode) {
        approveService.registerFavorites(formCode);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @DeleteMapping("/favorites/{num}")
    public ResponseEntity<String> deleteFavorites(@PathVariable("num") int formCode) {
        approveService.removeFavorites(formCode);
        return ResponseEntity.ok("ok");
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/favorites")
    public ResponseEntity<List<FavoritesResDTO>> getFavorites() {
        List<FavoritesResDTO> formCodeList = approveService.getFavorites();
        return ResponseEntity.ok(formCodeList);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam ("filePath") String resourcePath) {
        byte[] data = s3Service.downloadFile(resourcePath);
        ByteArrayResource resource = new ByteArrayResource(data);
        HttpHeaders headers = buildHeaders(resourcePath, data);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(resource);
    }

    private HttpHeaders buildHeaders(String resourcePath, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(data.length);
        //headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDisposition(FileCommonUtils.createContentDisposition(resourcePath));
        return headers;
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/fileNames/{num}")
    public ResponseEntity<List<FileResDTO>> getFileNames(@PathVariable("num") int approvalDocId) {
        List<FileResDTO> list = approveService.getFileNames(approvalDocId);
        return ResponseEntity.ok(list);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @GetMapping("/hasCancelApproval/{num}")
    public ResponseEntity<Boolean> getHasCancelApproval(@PathVariable("num") int approvalDocId){
        boolean hasApproval = approveService.getHasCancelApproval(approvalDocId);
        return ResponseEntity.ok(hasApproval);
    }

    @Authority(role = {Authority.Role.USER, Authority.Role.DEPT_ADMIN, Authority.Role.MASTER_ADMIN})
    @PostMapping("/cancelApproval/{num}")
    public ResponseEntity<String> cancelApprovalToTemporal(@PathVariable("num") int approvalDocId){
        System.out.println("cancel");
        approveService.changeApprovalToTemporal(approvalDocId);
        return ResponseEntity.ok("ok");
    }


}
