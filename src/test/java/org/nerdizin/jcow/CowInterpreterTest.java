package org.nerdizin.jcow;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class CowInterpreterTest {

	@Test
	public void testQuickExit() {

		final CowInterpreter cow = new CowInterpreter();
		final String program = "OOO MOo mOO";
		cow.run(program);
	}

	@Test
	public void testHelloWorld() throws IOException, URISyntaxException {
		
		final CowInterpreter cow = new CowInterpreter();
		cow.run(readFile("/helloWorld.cow"));
	}

	@Test
	public void testFibonacci() throws IOException, URISyntaxException {

		final CowInterpreter cow = new CowInterpreter();
		cow.run(readFile("/fibonacci.cow"));
	}
	
	private String readFile(final String file) throws IOException, URISyntaxException {
		final StringBuffer sb = new StringBuffer();
		for(final String line : Files.readAllLines(
				Paths.get(this.getClass().getResource(file)
						.toURI()), Charset.defaultCharset())) {
			sb.append(line + " ");
		}
		return sb.toString();
	}

	
}
