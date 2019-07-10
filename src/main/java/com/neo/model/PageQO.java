package com.neo.model;

/**
 *   分页查询需要继承的基类
 */
public abstract class PageQO {
	protected Integer page;      //页码
	protected Integer rows;     //每页多少个
	protected Integer startRow = 0;  //通过 setLimit计算后得到的结果 开始
	protected Integer total;         //通过 setLimit计算后得到的结果 总工显示多少个
	public void setLimit(){
		if(page==null||page<1){
			this.page = 1;
		}
		if(rows==null||rows<1){
			this.rows = 10;
		}
		this.startRow=(page-1)*rows;
	}
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
//		int b = total%rows;
//		int r =total/rows;
//		r = b == 0?r:r+1;
//		this.pages =r;
	}
	
}
