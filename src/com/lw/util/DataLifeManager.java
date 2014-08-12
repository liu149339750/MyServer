package com.lw.util;

import java.util.ArrayList;
import java.util.List;

public class DataLifeManager {

	private static DataLifeManager mLifeManager;
	private List<DownloadInfo> mData;
	
	private DataLifeManager(){
		mData = new ArrayList<DataLifeManager.DownloadInfo>();
	}
	
	public static DataLifeManager getInstance(){
		if(mLifeManager == null)
			mLifeManager = new DataLifeManager();
		return mLifeManager;
	}
	
	public void addInfo(String host,int device){
		checkInfo();
		DownloadInfo info = new DownloadInfo();
		info.device = device;
		info.host = host;
		info.time = System.currentTimeMillis();
		mData.add(info);
	}
	
	public int isRecommand(String host){
		checkInfo();
		for(DownloadInfo info : mData){
			if(info.host.equals(host)){
				mData.remove(info);
				return info.device;
			}
		}
		return -1;
	}
	
	private void checkInfo() {
		long now = System.currentTimeMillis();
		long durtion = 30 * 60 * 1000;
		List<DownloadInfo> removes = new ArrayList<DataLifeManager.DownloadInfo>();
		for(DownloadInfo info : mData){
			long time = info.time;
			if(now - time > durtion)
				removes.add(info);
		}
		mData.removeAll(removes);
	}

	public static class DownloadInfo{
		public String host;
		public int device;
		public long time;
		
//		@Override
//		public boolean equals(Object obj) {
//			DownloadInfo o = (DownloadInfo) obj;
//			return o.url.equals(url) && o.device == device;
//		}
	}
	
}
