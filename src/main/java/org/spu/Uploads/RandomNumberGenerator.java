package org.spu.Uploads;

import java.util.Random;

import org.springframework.context.annotation.Configuration;


@Configuration
public class RandomNumberGenerator {

	public int generate(){
		
		Random rd = new Random();
		return rd.nextInt(40);
	}
}
