package com.epm.gestepm.modelapi.common.utils.datatables;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

/**
 * The Class DataTableRequest.
 *
 * @author pavan.solapure
 */
public class DataTableRequest<T> {

	/** The unique id. */
	private String uniqueId;

	/** The draw. */
	private String draw;

	/** The start. */
	private Integer start;

	/** The length. */
	private Integer length;

	/** The search. */
	private String search;

	/** The regex. */
	private boolean regex;

	/** The columns. */
	private List<DataTableColumnSpecs> columns;

	/** The order. */
	private DataTableColumnSpecs order;

	/** The is global search. */
	private boolean isGlobalSearch;

	/**
	 * Instantiates a new data table request.
	 *
	 * @param request the request
	 */
	public DataTableRequest(HttpServletRequest request) {
		prepareDataTableRequest(request);
	}

	/**
	 * Gets the unique id.
	 *
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the unique id.
	 *
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * Sets the search.
	 *
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * Checks if is regex.
	 *
	 * @return the regex
	 */
	public boolean isRegex() {
		return regex;
	}

	/**
	 * Sets the regex.
	 *
	 * @param regex the regex to set
	 */
	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public List<DataTableColumnSpecs> getColumns() {
		return new ArrayList<>(columns);
	}

	/**
	 * Sets the columns.
	 *
	 * @param columns the columns to set
	 */
	public void setColumns(List<DataTableColumnSpecs> columns) {
		this.columns = new ArrayList<>(columns);
	}

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public DataTableColumnSpecs getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order the order to set
	 */
	public void setOrder(DataTableColumnSpecs order) {
		this.order = order;
	}

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public String getDraw() {
		return draw;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw the draw to set
	 */
	public void setDraw(String draw) {
		this.draw = draw;
	}

	/**
	 * Checks if is global search.
	 *
	 * @return the isGlobalSearch
	 */
	public boolean isGlobalSearch() {
		return isGlobalSearch;
	}

	/**
	 * Sets the global search.
	 *
	 * @param isGlobalSearch the isGlobalSearch to set
	 */
	public void setGlobalSearch(boolean isGlobalSearch) {
		this.isGlobalSearch = isGlobalSearch;
	}

	/**
	 * Prepare data table request.
	 *
	 * @param request the request
	 */
	private void prepareDataTableRequest(HttpServletRequest request) {

		Enumeration<String> parameterNames = request.getParameterNames();

		if (parameterNames.hasMoreElements()) {

			this.setStart(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_NO)));
			this.setLength(Integer.parseInt(request.getParameter(PaginationCriteria.PAGE_SIZE)));
			this.setUniqueId(request.getParameter("_"));
			this.setDraw(request.getParameter(PaginationCriteria.DRAW));

			this.setSearch(request.getParameter("search[value]"));
			this.setRegex(Boolean.valueOf(request.getParameter("search[regex]")));

			int sortableCol = -1;
			String ordableFirstColumn = request.getParameter("order[0][column]");
			
			if (ordableFirstColumn != null) {
				sortableCol = Integer.parseInt(ordableFirstColumn);
			}
			
			List<DataTableColumnSpecs> columnas = new ArrayList<>();

			if (!DataTableUtil.isObjectEmpty(this.getSearch())) {
				this.setGlobalSearch(true);
			}

			int maxParamsToCheck = getNumberOfColumns(request);

			String parameterColumns = "columns[";
			String parameterData = "][data]";

			for (int i = 0; i < maxParamsToCheck; i++) {
				if (null != request.getParameter(parameterColumns + i + parameterData)
						&& !"null".equalsIgnoreCase(request.getParameter(parameterColumns + i + parameterData))
						&& !DataTableUtil.isObjectEmpty(request.getParameter(parameterColumns + i + parameterData))) {
					DataTableColumnSpecs colSpec = new DataTableColumnSpecs(request, i);
					if (i == sortableCol) {
						this.setOrder(colSpec);
					}
					columnas.add(colSpec);

					if (!DataTableUtil.isObjectEmpty(colSpec.getSearch())) {
						this.setGlobalSearch(false);
					}
				}
			}

			if (!DataTableUtil.isObjectEmpty(columnas)) {
				this.setColumns(columnas);
			}
		}
	}

	private int getNumberOfColumns(HttpServletRequest request) {
		Pattern p = Pattern.compile("columns\\[[0-9]+\\]\\[data\\]");
		@SuppressWarnings("rawtypes")
		Enumeration params = request.getParameterNames();
		List<String> lstOfParams = new ArrayList<>();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			Matcher m = p.matcher(paramName);
			if (m.matches()) {
				lstOfParams.add(paramName);
			}
		}
		return lstOfParams.size();
	}

	/**
	 * Gets the pagination request.
	 *
	 * @return the pagination request
	 */
	public PaginationCriteria getPaginationRequest() {

		PaginationCriteria pagination = new PaginationCriteria();
		pagination.setPageNumber(this.getStart());
		pagination.setPageSize(this.getLength());

		SortBy sortBy = null;
		if (!DataTableUtil.isObjectEmpty(this.getOrder())) {
			sortBy = new SortBy();
			sortBy.addSort(this.getOrder().getData(), SortOrder.fromValue(this.getOrder().getSortDir()));
		}

		FilterBy filterBy = new FilterBy();
		filterBy.setGlobalSearch(this.isGlobalSearch());
		for (DataTableColumnSpecs colSpec : this.getColumns()) {
			if (colSpec.isSearchable() && (!DataTableUtil.isObjectEmpty(this.getSearch()) || !DataTableUtil.isObjectEmpty(colSpec.getSearch()))) {
				filterBy.addFilter(colSpec.getData(), (this.isGlobalSearch()) ? this.getSearch() : colSpec.getSearch());
			}
		}

		pagination.setSortBy(sortBy);
		pagination.setFilterBy(filterBy);

		return pagination;
	}
}
