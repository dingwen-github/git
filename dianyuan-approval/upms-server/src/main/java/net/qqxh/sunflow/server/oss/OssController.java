package net.qqxh.sunflow.server.oss;

import net.qqxh.common.ossclient.FolderType;
import net.qqxh.common.ossclient.MagicOssClient;
import net.qqxh.sunflow.server.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/oss")
public class OssController extends BaseController {
    @Autowired
    private MagicOssClient magicOssClient;

    @RequestMapping("/put")
    public Object upload(@RequestParam("file") MultipartFile file,String fix) {
        String url = "";
        try {
            url = magicOssClient.upload("upms", "userAvatar", FolderType.yyyyMM, file.getInputStream(),fix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseSuccess(url);
    }
}
