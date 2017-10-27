package com.sap.opensap.datamodel.successfactors;

import lombok.Data;

import java.util.List;

import com.sap.cloud.sdk.result.ElementName;

@Data
public class JobDetails
{
    @ElementName( "costCenter" )
    private String costCenter;

    @ElementName( "department" )
    private String department;

    @ElementName( "division" )
    private String division;

    @ElementName( "employmentType" )
    private String employmentType;

    @ElementName( "jobTitle" )
    private String jobTitle;

    @ElementName( "payGrade" )
    private String payGrade;

    @Data
    public static class JobDetailsContainer
    {
        @ElementName( "results" )
        private List<JobDetails> jobDetails;
    }

    @Data
    public static class EmploymentDetails
    {
        @ElementName( "jobInfoNav" )
        private JobDetails.JobDetailsContainer jobDetailsContainer;
    }

    @Data
    public static class EmploymentDetailsContainer
    {
        @ElementName( "results" )
        private List<EmploymentDetails> employmentDetails;
    }
}
