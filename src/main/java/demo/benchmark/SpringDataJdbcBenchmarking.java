package demo.benchmark;

import java.util.concurrent.TimeUnit;

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

import demo.jdbc.JdbcConfig;
import demo.jdbc.Product;
import demo.jdbc.ProductRepository;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class SpringDataJdbcBenchmarking {
	
	private ProductRepository productJdbcRepository;
	
	private int id;

    @Setup
    public void setup() {
    	initEm();

        Product product = new Product();
        product.setName("phone");
        productJdbcRepository.save(product);
    	id = product.getId();
    }

    private void initEm() {
    	AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(
				JdbcConfig.class);
        productJdbcRepository = context.getBean(ProductRepository.class);
	}

	@Benchmark
    public demo.jdbc.Product springDataJdbcQuery() {
        return productJdbcRepository.findByName("phone");
    }
	
    //@Benchmark
    public demo.jdbc.Product insertProductJdbc() {
        demo.jdbc.Product product = new demo.jdbc.Product();
        product.setName("phone");
        return productJdbcRepository.save(product);
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
