import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class GZIPUtil{
	
	/**
	 * to compress and encode a string
	 * @param srcTxt
	 * @return
	 * @throws IOException
	 */
	public static String compress(String srcTxt){	
		ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		try{
			GZIPOutputStream zos = new GZIPOutputStream(rstBao);
			zos.write(srcTxt.getBytes());
			IOUtils.closeQuietly(zos);	
		} catch (Exception e) {
			return "";
		}
		byte[] bytes = rstBao.toByteArray();
		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * to uncompress and decode a string
	 * @param zippedBase64Str
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String uncompress(String zippedBase64Str){
		String result = "";
		byte[] bytes = Base64.getDecoder().decode(zippedBase64Str);
		GZIPInputStream zi = null;
		try{
			zi = new GZIPInputStream(new ByteArrayInputStream(bytes));
			result = IOUtils.toString(zi);
		} 
		catch (Exception e) {}
		finally {
			IOUtils.closeQuietly(zi);
		}
		return result;
	}
	
	/**
	 * Compress the uploaded file
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] compressFile(byte[] fileData, String fileName) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try(ZipOutputStream outputStream = new ZipOutputStream(baos)) {

		  ZipEntry fileToZip = new ZipEntry(fileName); 

		  outputStream.putNextEntry(fileToZip);
		  outputStream.write(fileData);
		  outputStream.closeEntry();

		  } catch(IOException ioe) {
			  log.debug("Exception occurred while compressing the file");
		    throw new IOException();
		  }
		return baos.toByteArray();
	}
	
	 public static Path unzip(String zipFilePath, String unzipLocation) throws IOException {
		 Path filePath = null;
	        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
	            ZipEntry entry = zipInputStream.getNextEntry();
	            if (entry != null) {
	                 filePath = Paths.get(unzipLocation, entry.getName());
	                if (!entry.isDirectory()) {
	                    unzipFiles(zipInputStream, filePath);
	                } 
	                zipInputStream.closeEntry();
	                entry = zipInputStream.getNextEntry();
	            }
	            return filePath;
	        }
	    }
	 
	 public static  void unzipFiles(ZipInputStream zipInputStream, Path unzipFilePath) throws IOException  {
		 	log.debug("GZIPUtil:Dest file Location::"+unzipFilePath);
	        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unzipFilePath.toAbsolutePath().toString()))) {
	            byte[] bytesIn = new byte[1024];
	            int read = 0;
	            while ((read = zipInputStream.read(bytesIn)) != -1) {
	                bos.write(bytesIn, 0, read);
	            }
	        }
	    }
	 
	public static List<Path> unzip(byte[] data, String dirName) throws IOException {
		File destDir = new File(dirName);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		Path filePath = null;
		List<Path> pathList = new ArrayList<>();
		try(ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(data))) {
			ZipEntry entry = zipIn.getNextEntry();
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_");
			while (entry != null) {
				StringBuilder sb = new StringBuilder(dateFormat.format(date));
				filePath = Paths.get(dirName + File.separator + sb.append(entry.getName()));
				if (!entry.isDirectory()) {
					// if the entry is a file, extracts it
					unzipFiles(zipIn, filePath);
				} else {
					// if the entry is a directory, make the directory
					Files.createDirectory(filePath);
				}
				pathList.add(filePath);
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
		}
		
		return pathList;
	}
}
