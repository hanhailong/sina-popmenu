# sina-popmenu
高仿新浪微博弹框菜单

效果图如下：

![](https://github.com/hanhailong/AndroidStudyResources/blob/master/screenshot/tanhuang-offical-demo3.gif?raw=true)

# 示例代码

	PopMenu popMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
	                .addMenuItem(new PopMenuItem("文字", getResources().getDrawable(R.drawable.tabbar_compose_idea)))
	                .addMenuItem(new PopMenuItem("照片/视频", getResources().getDrawable(R.drawable.tabbar_compose_photo)))
	                .addMenuItem(new PopMenuItem("头条文章", getResources().getDrawable(R.drawable.tabbar_compose_headlines)))
	                .addMenuItem(new PopMenuItem("签到", getResources().getDrawable(R.drawable.tabbar_compose_lbs)))
	                .addMenuItem(new PopMenuItem("点评", getResources().getDrawable(R.drawable.tabbar_compose_review)))
	                .addMenuItem(new PopMenuItem("更多", getResources().getDrawable(R.drawable.tabbar_compose_more)))
	                .build();
	popMenu.show();

# TODO
* 添加Item点击效果
* 添加点击Item底部文字
* 添加点击Item事件回调
* 其他