package co.com.pragma.kafka.consumer.mapper;

import co.com.pragma.kafka.consumer.dto.BootcampReportEventDto;
import co.com.pragma.model.report.command.RegisterBootcampReportCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BootcampReportEventMapper {

    RegisterBootcampReportCommand toRegisterBootcampReportCommand(BootcampReportEventDto bootcampReportEventDto);
}
