package drools.poc.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.drools.template.jdbc.ResultSetGenerator;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import com.cc.drools.model.Customer;
import com.cc.drools.model.Customer.CustomerType;

public class UsingDRLTemplate {

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException, IOException {

		KieServices services = KieServices.Factory.get();
		
		
		
		
		
//		Resource resource = ResourceFactory.newClassPathResource(
//				"RulesTemplate.drt", UsingDRLTemplate.class);
//		KieFileSystem kieFileSystem = services.newKieFileSystem().write(
//				resource);
		
		

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "bms", "abc");
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery("select id,customer_type,cust_year,disc from disc_rules");
		
		while(resultSet.next()){
			
		System.out.println(	resultSet.getInt(4));
		}
		
		
		ResultSetGenerator rg = new ResultSetGenerator();
		String drl = rg.compile(resultSet, new FileInputStream(new File("C:\\Users\\admin\\DroolsWorkSpace\\DroolsPOC\\src\\main\\resources\\RulesTemplate.drt")));
		
		KieHelper kieHelper = new KieHelper();
		
		byte[] b1 = drl.getBytes();
		Resource resource1 = services.getResources().newByteArrayResource(b1);
		kieHelper.addResource(resource1, ResourceType.DRL);

		KieBase kieBase = kieHelper.build();
		
		
	
	//	KieBuilder kieBuilder = services.newKieBuilder(kieFileSystem);
	
	//	kieBuilder.buildAll();
		
		
//		KieRepository repository = services.getRepository();
//
//		ReleaseId defaultReleaseId = repository.getDefaultReleaseId();
//		KieContainer kieContainer = services
//				.newKieContainer(defaultReleaseId);
//
//		KieSession session = kieContainer.newKieSession();
//		
		KieSession session = kieBase.newKieSession();
		Customer customer = new Customer(CustomerType.BUSINESS, 5);
		session.insert(customer);
		
		int fireAllRules = session.fireAllRules();
		System.err.println("Fired Rules :"+fireAllRules);
		
		
		
		
		
		System.err.println(customer.getDiscount());
		
	}

}
