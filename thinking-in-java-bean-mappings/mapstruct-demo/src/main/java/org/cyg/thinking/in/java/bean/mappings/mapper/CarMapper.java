package org.cyg.thinking.in.java.bean.mappings.mapper;

import org.cyg.thinking.in.java.bean.mappings.dto.Car;
import org.cyg.thinking.in.java.bean.mappings.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mappings(@Mapping(source = "numberOfSeats", target = "seatCount"))
    CarDto carToCarDto(Car car);

    /***
     * 发编译实例化对象后的源码
     * package org.cyg.thinking.in.java.bean.mappings.mapper;
     *
     * import org.cyg.thinking.in.java.bean.mappings.dto.Car;
     * import org.cyg.thinking.in.java.bean.mappings.dto.CarDto;
     * import org.cyg.thinking.in.java.bean.mappings.enums.CarType;
     * import org.cyg.thinking.in.java.bean.mappings.mapper.CarMapper;
     *
     * public class CarMapperImpl
     * implements CarMapper {
     *     public CarDto carToCarDto(Car car) {
     *         if (car == null) {
     *             return null;
     *         }
     *         CarDto carDto = new CarDto();
     *         carDto.setSeatCount(car.getNumberOfSeats());
     *         carDto.setMake(car.getMake());
     *         if (car.getType() != null) {
     *             carDto.setType(car.getType().name());
     *         }
     *         return carDto;
     *     }
     * }
     */
}
