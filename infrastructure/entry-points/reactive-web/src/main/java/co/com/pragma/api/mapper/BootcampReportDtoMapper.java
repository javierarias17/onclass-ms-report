package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.BootcampReportInDto;
import co.com.pragma.api.dto.BootcampReportOutDto;
import co.com.pragma.model.report.BootcampReport;
import co.com.pragma.model.report.command.RegisterBootcampReportCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BootcampReportDtoMapper {

    RegisterBootcampReportCommand toRegisterBootcampReportCommand(BootcampReportInDto bootcampReportInDto);

    BootcampReportOutDto toBootcampReportOutDto(BootcampReport bootcampReport);
}
