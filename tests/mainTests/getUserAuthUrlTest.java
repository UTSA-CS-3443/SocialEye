package mainTests;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import reddit.RedditOAuth;

public class getUserAuthUrlTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		if(Desktop.isDesktopSupported())
		{
			URL url = new URL(RedditOAuth.getUserAuthUrl("test"));
			String nullFragment = null;
			URI uri;
			try {
				uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), nullFragment );
		    	Desktop.getDesktop().browse(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

}
