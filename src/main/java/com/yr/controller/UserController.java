package com.yr.controller;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yr.entity.User;
import com.yr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private FastFileStorageClient storageClient;

    //上传
    @RequestMapping(value = "upload")
    public String upload(User user, @RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            if (!file.getOriginalFilename().equals("")) {
                String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

                Set<MetaData> metaDataSet = new HashSet<>();
                metaDataSet.add(new MetaData("上传用户", "root"));
                metaDataSet.add(new MetaData("key", String.valueOf(System.currentTimeMillis())));
                metaDataSet.add(new MetaData("时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

                StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), fileName, metaDataSet);
                user.setFileName(storePath.getPath().substring(storePath.getPath().lastIndexOf("/")).replace("/", ""));
                user.setGroupName(storePath.getGroup());
                user.setFilePath(storePath.getPath());
                userService.add(user);
            } else {
                System.out.println("请选择文件上传");
            }
        }
        return "redirect:/user/list";
    }

    //查看元数据
    @RequestMapping(value = "yuan/{id}")
    public String yuan(@PathVariable("id") Integer id) {
        User user = userService.getUpdate(id);
        Set<MetaData> metaDataSet = storageClient.getMetadata(user.getGroupName(), user.getFilePath());
        for (MetaData metaData : metaDataSet) {
            System.out.println(metaData.toString());
        }
        return "redirect:/user/list";
    }

    //修改元数据
    @RequestMapping(value = "yuanupdate/{id}")
    public String yuanUpdate(@PathVariable("id") Integer id) {
        User user = userService.getUpdate(id);
        Set<MetaData> metaDataSet = new HashSet<>();
        metaDataSet.add(new MetaData("上传用户", "root"));
        metaDataSet.add(new MetaData("key", String.valueOf(System.currentTimeMillis())));
        metaDataSet.add(new MetaData("时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        storageClient.overwriteMetadata(user.getGroupName(), user.getFilePath(), metaDataSet);
        return "redirect:/user/list";
    }

    //删除
    @RequestMapping(value = "del/{id}")
    public String delete(@PathVariable("id") Integer id) {
        User user = userService.getUpdate(id);
        System.out.println("删除");
        storageClient.deleteFile(user.getGroupName() + "/" + user.getFilePath());
        userService.delete(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "download/{id}")
    public void download(@PathVariable("id") Integer id, HttpServletResponse resp) throws Exception {
        User user = userService.getUpdate(id);

        resp.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(user.getFileName(), "UTF-8"));
        resp.setContentType("application/octet-stream; charset=UTF-8");

        byte[] bytes = storageClient.downloadFile(user.getGroupName(), user.getFilePath(), new DownloadByteArray());
        OutputStream os = resp.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }

    @RequestMapping(value = "list")
    public String select(Map<String, List<User>> map) {
        System.out.println("查询" + "    " + userService.select());
        map.put("lst", userService.select());
        return "user/list";
    }


}
