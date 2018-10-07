package drools.poc.app;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import com.cc.drools.model.Customer;
import com.cc.drools.model.Customer.CustomerType;

public class MyApp {

	public static void main(String[] args) {
		KieServices kieServices = KieServices.Factory.get();
		// KieContainer kieClasspathContainer =
		// kieServices.getKieClasspathContainer();

		Resource resource = ResourceFactory.newClassPathResource(
				"Discount.xls", MyApp.class);
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(
				resource);

		KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
		kieBuilder.buildAll();

		KieRepository repository = kieServices.getRepository();

		ReleaseId defaultReleaseId = repository.getDefaultReleaseId();
		KieContainer kieContainer = kieServices
				.newKieContainer(defaultReleaseId);

		KieSession kieSession = kieContainer.newKieSession();

		Customer customer = new Customer(CustomerType.BUSINESS, 2);
		Customer customer1 = new Customer(CustomerType.INDIVIDUAL, 2);
		Customer customer2 = new Customer(CustomerType.INDIVIDUAL, 4);
		kieSession.insert(customer);
		kieSession.insert(customer1);
		kieSession.insert(customer2);

		System.err.println("Before Rule Fire" + customer.getDiscount());

		System.err.println("Before Rule Fire 1 " + customer1.getDiscount());

		System.err.println("Before Rule Fire 2 " + customer2.getDiscount());

		int fireAllRules = kieSession.fireAllRules();
		System.err.println("Rules Fired" + fireAllRules);

		System.err.println("After Rule Fire" + customer.getDiscount());

		System.err.println("After Rule Fire 1 " + customer1.getDiscount());

		System.err.println("After Rule Fire 2 " + customer2.getDiscount());
	}

}
