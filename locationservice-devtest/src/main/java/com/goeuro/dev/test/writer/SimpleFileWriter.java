package com.goeuro.dev.test.writer;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.goeuro.dev.test.util.Message;

@Component
public class SimpleFileWriter {

	@Value("${file.dir}")
	private String directoryPath;

	@Value("${file.suffix}")
	private String fileSuffix;
	@Value("${file.ext}")
	private String fileExt;
	
	private static final String SYSTEM_PROP_USER_DIR = "user.dir";
	private static final String SYSTEM__PROP_FILE_SEPARATOR = "file.separator";

	public void write(String content, String locationName) throws IOException {

		FileWriter fileWriter = null;
		String fileName = generateFullFileName(locationName);

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append(content);
			Message.display("The Csv file has been successfully created at %s", fileName);
		} finally {
			fileWriter.flush();
			fileWriter.close();
		}
	}

	protected String generateFullFileName(String locationName) {
		StringBuffer fileAbsolutePathWithNameAndExt = new StringBuffer();
		fileAbsolutePathWithNameAndExt.append(getDirectory());
		fileAbsolutePathWithNameAndExt.append(System.getProperty(SYSTEM__PROP_FILE_SEPARATOR));
		fileAbsolutePathWithNameAndExt.append(locationName);
		fileAbsolutePathWithNameAndExt.append(fileSuffix);
		fileAbsolutePathWithNameAndExt.append(fileExt);
		return fileAbsolutePathWithNameAndExt.toString();
	}

	protected String getDirectory() {
		if (StringUtils.isEmpty(directoryPath) || ".".equals(directoryPath)) {
			return System.getProperty(SYSTEM_PROP_USER_DIR);
		} else {
			return directoryPath;
		}
	}

}
