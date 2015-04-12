package com.zm.common.pagination;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.HtmlUtils;

import com.zm.common.constant.CommonConstants;
import com.zm.common.constant.StringConstant;
import com.zm.common.utils.StringUtils;


@Repository
public class BasePagination<T> implements Serializable {

	private static final long serialVersionUID = -4319028035771574084L;

	/**
	 * 默认当前页
	 */
	protected static final Integer DEFAULT_PAGE = 0;
	/**
	 * 默认一页数量
	 */
	@Value("default_limit")
	protected static int default_limit;
	
	/**
	 * 默认页面显示索引长度
	 */
	protected static final Integer DEFAULT_DISPLAYINDEXLEN = 7;

	/**
	 * 前台页面显示页数索引长度
	 */
	protected Integer displayIndexLen = DEFAULT_DISPLAYINDEXLEN;
	/**
	 * 当前页
	 */
	protected Integer currentPage = DEFAULT_PAGE;
	/**
	 * 一页数量
	 */
	
	protected Integer limit = default_limit;
	/**
	 * 总数，如果页面传入了total，说明是分页跳转，那么则不需要再查询总数。
	 */
	protected Integer total;
	/**
	 * 排序列名
	 */
	protected String sort;
	/**
	 * 顺序or倒序。'asc' or 'desc'
	 */
	protected String dir;
	/**
	 * search result
	 */
	protected Collection<T> result;
	/**
	 * 传入的其他搜素参数
	 */
	protected Map<String, String> params;
	/**
	 * execute elapsed time,unit is seconds.
	 */
	protected Long elapsedTime;
	
	protected LinkedList<Integer> displayIndexs;
	
	//protected String filterParamReg = "[\\<\\>\\\"\\'　\\ ]";//需要过了掉param值中的字符
	protected String filterParamReg = "[\\<\\>\\\"\\']";//需要过了掉param值中的字符

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public Integer getTotalPage() {
		return total == null ? 0 : (total + limit - 1) / limit;
	}

	/**
	 * 获取前一页数。如果没有则返回null
	 * 
	 * @return
	 */
	public Integer getBeforePage() {
		return this.getCurrentPage() == 0 ? null : this.currentPage - 1;
	}

	/**
	 * 获取下一页数。如果没有则返回null
	 * 
	 * @return
	 */
	public Integer getNextPage() {
		return this.getCurrentPage() + 1 < getTotalPage() ? this.currentPage + 1
				: null;
	}

	/**
	 * 获取带查询参数的map对象。start：查询起始位。limit：查询多少条
	 * 
	 * @return
	 */
	public Map<String, Object> getSearchParamsMap() {
		decodeHtmlFormatParams();
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.params != null) {
			map.putAll(params);
		}
		map.put(CommonConstants.PAGE_SORT, this.sort);
		if(!StringConstant.DESC.equalsIgnoreCase(this.dir)){
			this.dir = StringConstant.ASC;
		}
		map.put(CommonConstants.PAGE_DIR, this.dir);
		map.put(CommonConstants.PAGE_START, this.getCurrentPage() * this.limit);
		map.put(CommonConstants.PAGE_LIMIT, this.limit);
		return map;
	}
	
	/**
	 * 如果传入的参数有的是html格式的（如：&#20013;&#22269; 格式），则decode。
	 */
	public void decodeHtmlFormatParams(){
		if(null != params && !params.isEmpty()){
			for(String key:params.keySet()){
				String value = params.get(key);
				if(StringUtils.isNotEmpty(value)){
					value = HtmlUtils.htmlUnescape(value);
					 value = value.trim().replaceAll(filterParamReg, "");				       
					params.put(key, value);
				}				
			}
		}
	}
	
	/**
	 * 前台分页显示集合
	 * 
	 * @return
	 */
	public LinkedList<Integer> getDisplayIndexs() {
		if(null == displayIndexs){
			LinkedList<Integer> list = new LinkedList<Integer>();
			//如果一页都没有，则返不添加。
			int totalPage = getTotalPage().intValue();
			if ( totalPage > 0) {
				// 添加当前页和当前要前面
				int currentPage = getCurrentPage();
				int varPrePage = currentPage;
				int maxPre = displayIndexLen / 2; // 当前页前面最长显示
				list.add(currentPage);
				while ((--varPrePage) >= 0 && (--maxPre) >= 0) {
					list.addFirst(varPrePage);
				}
				// 添加当前后面页
				int varNexPage = currentPage;
				while ((++varNexPage) < totalPage && list.size() < displayIndexLen) {
					list.addLast(varNexPage);					
				}
				//补齐前面	
				varPrePage++;
				while ((--varPrePage) >= 0 && list.size() < displayIndexLen) {
					list.addFirst(varPrePage);
				}
			}
			displayIndexs = list;
		}
		return displayIndexs;
	}
	
	public Integer getDisplayIndexsLen(){
		return getDisplayIndexs().size();
	}

	/**
	 * 是否需要设置总数,当页面没有传入total，则为null，那么需要设置总数。
	 * 
	 * @return
	 */
	public boolean isNeedSetTotal() {
		return total == null ? true : false;
	}

	public Integer getCurrentPage() {
		// 如果查询页数大于总页数，设置查询页数为总页数
		if (this.currentPage + 1 > this.getTotalPage()) {
			currentPage = this.getTotalPage() - 1;
		}
		// 如果查询页数小于0，设置查询页数为0
		if (this.currentPage < 0) {
			currentPage = 0;
		}
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		if(currentPage != null){
			this.currentPage = currentPage;
		}
	}
	
	public void putParam(String key,String val){
		if(null==params){
			params = new HashMap<String,String>();
		}
		params.put(key, val);
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Collection<T> getResult() {
		return result;
	}

	public void setResult(Collection<T> result) {
		this.result = result;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public void setDisplayIndexLen(Integer displayIndexLen) {
		this.displayIndexLen = displayIndexLen;
	}

	public void setFilterParamReg(String filterParamReg) {
		this.filterParamReg = filterParamReg;
	}
	
	@Value("${default_limit}")
	public  void setDefault_limit(int default_limit) {
		BasePagination.default_limit = default_limit;
	}
}
