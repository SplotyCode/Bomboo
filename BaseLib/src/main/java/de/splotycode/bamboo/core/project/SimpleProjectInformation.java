package de.splotycode.bamboo.core.project;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class SimpleProjectInformation {

    private File bambooFile;
    private String name;

}
