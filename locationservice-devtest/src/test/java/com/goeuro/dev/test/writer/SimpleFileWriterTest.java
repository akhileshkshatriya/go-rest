package com.goeuro.dev.test.writer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class SimpleFileWriterTest {

	@InjectMocks
	private SimpleFileWriter simpleFileWriter = new SimpleFileWriter();

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static final String CONTENT = "1234,name,location,1234.56,6543.21";
	private static final String LOCATION_NAME = "test-location";
	private static final String FILE_NAME = LOCATION_NAME + "_Location_Details.csv";
	private static final String SEPARATOR = System.getProperty("file.separator");
	private static final String USER_DIR = System.getProperty("user.dir");

	private String path;

	@Before
	public void setup() throws IOException {
		path = temporaryFolder.getRoot().getPath();
		ReflectionTestUtils.setField(simpleFileWriter, "directoryPath", path);
		ReflectionTestUtils.setField(simpleFileWriter, "fileSuffix", "_Location_Details");
		ReflectionTestUtils.setField(simpleFileWriter, "fileExt", ".csv");
	}

	@Test
	public void givenContent_ShouldCreateCSVFileWithContent() throws IOException {
		simpleFileWriter.write(CONTENT, LOCATION_NAME);
		File file = new File(path + SEPARATOR + FILE_NAME);
		assertTrue(file.exists());
		byte[] encoded = Files.readAllBytes(file.toPath());
		String fileContent = new String(encoded, "UTF-8");
		assertTrue(CONTENT.equals(fileContent));
	}
	
	@Test
	public void testGenerateFullFileName(){
		String fullPath = path + SEPARATOR + FILE_NAME;
		assertEquals(fullPath,simpleFileWriter.generateFullFileName(LOCATION_NAME));
	}
	
	@Test
	public void testGetDirectoryWithUserDir(){
		ReflectionTestUtils.setField(simpleFileWriter, "directoryPath", ".");
		assertEquals(USER_DIR,simpleFileWriter.getDirectory());
	}
	
	@Test
	public void testGetDirectoryWithSetDir(){
		assertEquals(path,simpleFileWriter.getDirectory());
	}

}
