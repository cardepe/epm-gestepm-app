package com.epm.gestepm.modelapi.common.utils.datatables;

import lombok.Data;

import java.util.List;

/**
 * The Class DataTableResults.
 *
 * @author pavan.solapure
 * @param <T> the generic type
 */
@Data
public class DataTableResults<T> {

	/** The draw. */
	private String draw;

	/** The records filtered. */
	private String recordsFiltered;

	/** The records total. */
	private String recordsTotal;

	/** The list of data objects. */
	List<T> data;

}
