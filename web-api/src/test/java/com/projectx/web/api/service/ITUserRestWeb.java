package com.projectx.web.api.service;


public class ITUserRestWeb {
	
	/*
	  Ths is an integration test, we will use this later on
	 
	protected static Tomcat tomcat;

	@BeforeClass
	public static void setUp() throws Exception {

		//String webappDirLocation = "src/main/webapp/";
		String webappDirLocation = "target/api.war";
		
		tomcat = new Tomcat();
		tomcat.setPort( 8080 );
		
		
		//tomcat.setBaseDir(".");
		//tomcat.getHost().setAppBase(".");
		
		
		// Add AprLifecycleListener
		StandardServer server = (StandardServer) tomcat.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);
		
		
		StandardContext ctx = (StandardContext) tomcat.addWebapp("/api", new File(webappDirLocation).getAbsolutePath());
		
		// Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
		
		tomcat.start();
		//tomcat.getServer().await();

	}

	@AfterClass
    public static void tearDown() throws Exception {
        if (tomcat != null) {
            tomcat.stop();
            tomcat.destroy();
            tomcat = null;
        }
	}

	@Test
	public void testGetMethod() throws InterruptedException, ExecutionException {
		try {
			URL url = new URL("http://localhost:9000/api/userCreate");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			Scanner scanner;
			String response;
			if (conn.getResponseCode() != 200) {
				scanner = new Scanner(conn.getErrorStream());
				response = "Error From Server \n\n";
			} else {
				scanner = new Scanner(conn.getInputStream());
				response = "Response From Server \n\n";
			}
			scanner.useDelimiter("\\Z");
			System.out.println(response + scanner.next());
			scanner.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
