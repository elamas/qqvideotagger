package qqvideotagger.model.operations;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTagsOperation {
	
	public enum OPERATIONS {
	    ADD,
	    REMOVE; 
	}

	private OPERATIONS operation;
	private List<String> tags;
}
