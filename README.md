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
	                .setOnItemClickListener(new PopMenuItemListener() {
	                    @Override
	                    public void onItemClick(PopMenu popMenu, int position) {
	                        Toast.makeText(MainActivity.this, "你点击了第" + position + "个位置", Toast.LENGTH_SHORT).show();
	                    }
	                })
	                .build();	
	popMenu.show();


# 其他

如果你在使用的过程中遇到问题，请联系我，谢谢！！！