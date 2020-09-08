package org.cyg.thinking.in.java.bean.mappings;

import org.cyg.thinking.in.java.bean.mappings.dto.Car;
import org.cyg.thinking.in.java.bean.mappings.dto.CarDto;
import org.cyg.thinking.in.java.bean.mappings.enums.CarType;
import org.cyg.thinking.in.java.bean.mappings.mapper.CarMapper;
import org.junit.Test;

import java.io.IOException;


public class MapStructTest {

    @Test
    public void shouldMapCarToDto() throws IOException {
        //given
        Car car = new Car( "Morris", 5, CarType.SEDAN );

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );
        System.out.println(CarMapper.INSTANCE.getClass());
        while (true) {
            //then
            assert carDto != null;
            assert "Morris".equals(carDto.getMake());
            assert 5 == carDto.getSeatCount();
            assert "SEDAN".equals(carDto.getType()) ;
            System.in.read();
        }


    }
}
