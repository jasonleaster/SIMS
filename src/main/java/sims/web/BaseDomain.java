package sims.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseDomain implements Serializable{

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
