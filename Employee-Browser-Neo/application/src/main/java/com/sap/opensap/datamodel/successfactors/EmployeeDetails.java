package com.sap.opensap.datamodel.successfactors;

import lombok.Data;

import java.util.List;

import com.sap.cloud.sdk.result.ElementName;

@Data
public class EmployeeDetails
{
    @ElementName( "personIdExternal" )
    private String personIdExternal;

    @ElementName( "employmentNav" )
    private JobDetails.EmploymentDetailsContainer employmentDetailsContainer = null;

    /* internal property access */

    public boolean hasDetails() {
        if(employmentDetailsContainer==null) {
            return false;
        }
        final List<JobDetails.EmploymentDetails> employmentDetails = employmentDetailsContainer.getEmploymentDetails();
        if( employmentDetails == null
                || employmentDetails.size()!=1
                || employmentDetails.get(0).getJobDetailsContainer()==null
                || employmentDetails.get(0).getJobDetailsContainer().getJobDetails()==null )
        {
            return false;
        }
        final List<JobDetails> jobDetails = employmentDetails.get(0).getJobDetailsContainer().getJobDetails();
        return jobDetails!=null && jobDetails.size()==1;
    }

    public JobDetails getDetails() {
        return employmentDetailsContainer.getEmploymentDetails().get(0).getJobDetailsContainer().getJobDetails().get(0);
    }
}
