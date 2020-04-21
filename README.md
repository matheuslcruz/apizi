# APIzi

An easy API for authenticated HTTP requests and XML/JSON text processing

### Get token authentication

    ApiToken tokenA = new ApiToken("username", "password");
    ApiToken tokenB = new ApiToken("token");
    
### Fetch authenticated XML resource for xPath queries

	ApiXmlResource xmlStream = tokenA.xml("http://some-xml-based-api/some/resource.xml");
		
	String valueX = xmlStream.one("some/element");
	String valueY = xmlStream.one("an/unknown/element");
	String[] values = xmlStream.many("action/author/name");
	
### Fetch non-authenticated XML resource for xPath queries

	SomeResource xmlStream = ApiXmlResource.instantiate(
		"http://some-xml-based-api/some/resource.xml", SomeResource.class);
	
### Fetch authenticated XML resource as Java Object

	SomeObject someObject = tokenA.xml("http://some-xml-based-api/some/resource.xml", SomeObject.class);
		
	String someField = someObject.getSomeField();
	String someNestedField = someObject.getNested().getField();
	
### Fetch JSON Resource as Java Object

	AnotherObject = tokenB.json("http://some-json-based-api/some/resource.json", AnotherObject.class);
	
### Fetch non-authenticated XML resource for xPath queries

	SomeResource xmlStream = ApiJsonResource.instantiate(
		"http://some-xml-based-api/some/resource.xml", SomeResource.class);
	
