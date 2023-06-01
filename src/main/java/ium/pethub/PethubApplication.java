package ium.pethub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * 수정자: 조훈창
 * 수정일: 2023-06-01
 * 1. 게시물 등 createdAt 자동 기입을 위해 EnableJpaAuditing 추가
 */
@EnableJpaAuditing
@SpringBootApplication
public class PethubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PethubApplication.class, args);
	}

}
