package demo.benchmark;

import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import demo.jpa.JpaConfig;
import demo.jpa.Product;
import demo.jpa.ProductRepository;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class SpringDataJpaBenchmarking {
	
	private ProductRepository productRepository;
	
	private int id;

	private EntityManager entityManager;

    @Setup
    public void setup() {
    	initEm();
    	Product product = new Product();
    	product.setName("phone");
    	productRepository.save(product);
    	
    	id = product.getId();
    }

    private void initEm() {
    	AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(
				JpaConfig.class);
    	productRepository = context.getBean(ProductRepository.class);
    	entityManager = context.getBean(EntityManager.class);
	}

	@Benchmark
    public Product springDataJpaQuery() {
		entityManager.clear();
        return productRepository.findByName("phone");
    }
  
    //@Benchmark
    public Product insertProductJpa() {
        Product product = new Product();
        product.setName("phone");
        return productRepository.save(product);
    }
    

    public static void main(String... args) throws Exception {
        Options opts = new OptionsBuilder().include(".*")
                .warmupIterations(5)
                .measurementIterations(5)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS).jvmArgs("-server").forks(1).resultFormat(ResultFormatType.TEXT).build();

        new Runner(opts).run();
    }
}
