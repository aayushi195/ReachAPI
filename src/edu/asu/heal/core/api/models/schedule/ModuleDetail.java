package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModuleDetail {

	private String module;
	private Date startDate;
	private Date endDate;
	private List<DayDetail> schedule;

	public ModuleDetail() {
		module =null;
		startDate = null;
		endDate = null;
		schedule = new ArrayList<>();
	}

	public ModuleDetail(String module, Date startDate, Date endDate,List<DayDetail> schedule) {
		this.module = module;
		this.startDate = startDate;
		this.endDate = endDate;
		this.schedule = new ArrayList<>(schedule);
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<DayDetail> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<DayDetail> schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "ModuleDetails : {" +
				", module='" + this.module + '\'' +
				", startDate='" + this.startDate.toString() + '\'' +
				", endDate=" + this.endDate.toString() +
				", schedule=" + this.schedule.toString() +
				'}';
	}
}
