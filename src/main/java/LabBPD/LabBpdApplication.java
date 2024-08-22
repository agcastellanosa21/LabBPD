package LabBPD;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class LabBpdApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabBpdApplication.class, args);
	}

}

@Entity
class U {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long i;

	private String n;
	private int a;

	public U() {}

	public U(String n, int a) {
		this.n = n;
		this.a = a;
	}

	public Long getI() {
		return i;
	}

	public void setI(Long i) {
		this.i = i;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
}

interface R extends CrudRepository<U, Long> {}

@Service
class S {

	@Autowired
	private R r;


	public U c(String n, int a) {
		return r.save(new U(n, a));
	}

	public Iterable<U> g() {
		return r.findAll();
	}

	public U t(Long id, String n, int a) {
		U u = r.findById(id).orElse(null);
		if (u != null) {
			u.setN(n);
			u.setA(a);
			return r.save(u);
		}
		return null;
	}

	public void d(Long id) {
		r.deleteById(id);
	}
}

@RestController
@RequestMapping("/x")
class C {

	@Autowired
	private S s;


	@PostMapping
	public U post(@RequestParam String n, @RequestParam int a) {
		return s.c(n, a);
	}

	@GetMapping
	public Iterable<U> get() {
		return s.g();
	}

	@PutMapping("/{id}")
	public U put(@PathVariable Long id, @RequestParam String n, @RequestParam int a) {
		return s.t(id, n, a);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		s.d(id);
	}
}
