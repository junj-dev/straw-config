package cn.tedu.straw.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 * @author chenhaibao
 *
 */
public class ImgUtils {


	/**
	 * 上传原图到图片服务器
	 * @return
	 * @throws IOException 
	 */
	public static String upload(MultipartFile pic) throws IOException{
		String url=null;
			try {
				File file = FileUtils.multipartFileToFile(pic);
				 url= FastDFSClient.uploadFile(file);
				 FileUtils.delteTempFile(file);
				 return url;
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}


	}
	
	
}
