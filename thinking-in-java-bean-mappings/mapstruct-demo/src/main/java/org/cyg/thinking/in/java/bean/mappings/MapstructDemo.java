package org.cyg.thinking.in.java.bean.mappings;

import org.cyg.thinking.in.java.bean.mappings.dto.Car;
import org.cyg.thinking.in.java.bean.mappings.dto.CarDto;
import org.cyg.thinking.in.java.bean.mappings.enums.CarType;
import org.cyg.thinking.in.java.bean.mappings.mapper.CarMapper;
import org.mapstruct.Mapper;

/**
 * mapstruct 使用示例
 * @see Mapper
 */
public class MapstructDemo {

    public static void main(String[] args) {
        //given
        Car car = new Car( "Morris", 5, CarType.SEDAN );

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

        System.out.println(carDto);
        System.out.println();
    }
}
