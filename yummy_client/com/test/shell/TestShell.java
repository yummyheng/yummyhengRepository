package com.test.shell;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.cons.Constants4Yummy;
import com.test.main.ShellButtonListener;
import com.test.model.YummyLabelText;
import com.test.util.AutoProducting;
import com.test.util.RelateToFile;
import com.view.bean.YShell;

/**
 * 程序的主窗口
 * 备注:需要jar包org.eclipse.equinox.common
 * @author hengzai 时间：2013-8-2
 */
public class TestShell extends Shell{
	//定义工具包
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	//导航栏的声明
	private NaviComp naviComp;
	//包含放置选项卡的容器的面板容器的声明
	private CTabFolder tabFolder;
	private static CTabFolder container;
	//选项卡内容的哈希表声明
	public static HashMap<String, CTabItem> itemMap = new HashMap<String, CTabItem>();
	
	
	public static void main(String args[]) {
		//如果不存在则创建此实例
		Display display = Display.getDefault();
		//需要构造函数
		TestShell shell = new TestShell(display);
		//打开界面
		shell.open();
		//shell应用界面设置
		shell.layout();
		//消息循环 如果界面一直不销毁
		while(!shell.isDisposed()) {
			//如果不反复的读取和分派
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	//构造方法
	public TestShell(Display display) {
		//shell的默认参数 接近于窗体的样式风格
		super(display, SWT.SHELL_TRIM);
		//设置主页
		setMainPage();
		//填入内容
		createContents();
	}
	
	//防止出现Subclassing not allowed异常
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	protected void createContents() {
		//设置标题框文本:恒仔的程序  unicode编码
		setText("\u6052\u4ed4\u7684\u7a0b\u5e8f");
		//设置总窗口大小
		setSize(900,700);
		//匿名内部类 面板间的互动  从DOM中创建ISelectionChangedListener
		naviComp.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {
		//选项被点选的监听器
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			//选择项
			StructuredSelection selection = (StructuredSelection) event.getSelection();
			//定义xml元素
			Element element = (Element) selection.getFirstElement();
			final String uid = element.attributeValue("uid");
			String title = element.attributeValue("name");
			//输入参数检验
			if(uid == null || uid.trim().equals("")) {
				return;
			}
			//uid作为HashMap的key
			CTabItem tabItem = itemMap.get(uid);
			//实例为空时创建
			if(tabItem == null) {
				//创建只有关闭按钮的带标签内容控件
				tabItem = new CTabItem(tabFolder,SWT.CLOSE);
				
				/*
				 * 活动内容
				 */
				tabItem.setText("建立新的弹窗");
				tabItem.setImage(SWTResourceManager.getImage("./icons/three.png"));
				
				//新添加的组件初始化
				Composite newComposite = setNewComposite(tabItem);
				//编排输入界面
				HashMap<String, Text> text_map = setTextInputPage(newComposite);
				setButtonComposite(newComposite, text_map);
				newComposite.layout();
				formToolkit.paintBordersFor(newComposite);
				itemMap.put(uid, tabItem);
				//添加新增窗口销毁的监听器
				tabItem.addDisposeListener(new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent e) {
						itemMap.remove(uid);
					}
				});
			}
			tabFolder.setSelection(tabItem);
		}
		});
	}

	public static CTabFolder getContainer() {
		return container;
	}

	public static void setContainer(CTabFolder container) {
		TestShell.container = container;
	}
	
	/**
	 * 设置窗口布局
	 */
	private void setLayout4main() {
		//网格布局参数:列数 是否等分距离
		GridLayout gl = new GridLayout(1, false);
		gl.verticalSpacing = 0;
		//设置内容和边界距离
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		//设置布局
		setLayout(gl);
	}
	
	/**
	 * 设置窗口头部
	 */
	private void setShellHead() {
		//顶层面板 参数：父面板，风格 SWT.NONE默认式样
		Composite composite_top = new Composite(this, SWT.NONE);
		//设置背景颜色
		composite_top.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		//继承父类的模式
		composite_top.setBackgroundMode(SWT.INHERIT_FORCE);
		//设置网格布局 参数：3列(3个组件a1 a2 a3)，不等宽
		GridLayout gl_composite_top = new GridLayout(3, false);
		gl_composite_top.marginWidth = 0;
		gl_composite_top.marginHeight = 0;
		//组件布局 参数为窗口布局的样式 容器类独有 网格填充
		composite_top.setLayout(gl_composite_top);
		//新建布局数据类并放入数据 所有界面类共有参数:水平填充、垂直居中、能否人工缩放(水平可、垂直否) 网格占有行数(水平1， 垂直1)
		composite_top.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		//设置标签头 a1
		Label label = new Label(composite_top, SWT.NONE);
		label.setImage(SWTResourceManager.getImage("./icons/label.png"));
		
		//设置面板组件 a2 设立空白 撑位置
		Composite composite_3 = new Composite(composite_top, SWT.NONE);
		composite_3.setLayout(new GridLayout(1, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1, 1));
		
		//放超链接的面板 a3 超链接
		Composite composite_4 = new Composite(composite_top, SWT.NONE);
		composite_4.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		composite_4.setLayout(new GridLayout(4, false));
		//设置超链接 在a3内
		Link link_1 = new Link(composite_4, SWT.NONE);
		formToolkit.adapt(link_1, true, true);
		//unicode编码  主页
		link_1.setText("<a>\u4E3B\u9875</a>");
	}
	
	/**
	 * 设置窗口的中心部分
	 */
	private void setShellMiddle() {
		//分割框容器
		SashForm sashForm = new SashForm(this, SWT.NONE); 
		//两分割窗的间距
		sashForm.setSashWidth(5);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1, 1));
		//左面板容器
		setShellMiddleLeft(sashForm);
		//右面板容器
		setShellMiddleRight(sashForm);
		//分割框分割比例
		sashForm.setWeights(new int[] {2, 8});
	}
	
	/**
	 * 设置窗口主页
	 */
	private void setMainPage() {
		//窗口顶层标题栏
		setImage(SWTResourceManager.getImage(Constants4Yummy.SHELL_MAIN_TOPIC_ICON1));
		//设立总体布局参数
		setLayout4main();
		//设定头部
		setShellHead();
		//设定中心部分
		setShellMiddle();
	}
	
	/**
	 * 设置新开的窗口
	 * @param tabItem
	 * @return
	 */
	private Composite setNewComposite(CTabItem tabItem) {
		Composite newComposite = new Composite(tabFolder, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		//布局方式
		newComposite.setLayout(gridLayout);
		newComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		//设置事件
		tabItem.setControl(newComposite);
		return newComposite;
	}
	
	/**
	 * 设置中心左侧面板
	 */
	private void setShellMiddleLeft(SashForm sashForm) {
		Composite composite_left = new Composite(sashForm, SWT.NONE);
		//水平填充
		composite_left.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//包含放置选项卡的容器的面板容器 SWT.BORDER 一个有边框但没有标题栏的窗口
		CTabFolder tabFolder_left = new CTabFolder(composite_left, SWT.BORDER);
		formToolkit.adapt(tabFolder_left);
		//设置边框
		formToolkit.paintBordersFor(tabFolder_left);
		//与类型平台的主体颜色相类似并有渐变效果
		tabFolder_left.setSelectionBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		//选项卡 放在放置选项卡的容器中
		CTabItem tabItem_left = new CTabItem(tabFolder_left, SWT.NONE);
		//unicode 导航栏
		tabItem_left.setText("\u5BFC\u822A\u680F");
		//设置图像
		tabItem_left.setImage(SWTResourceManager.getImage("./icons/navi.png"));
		
		naviComp = new NaviComp(tabFolder_left, SWT.NONE);
		
		//设置左面板事件
		tabItem_left.setControl(naviComp);
		formToolkit.paintBordersFor(naviComp);
		tabFolder_left.setSelection(tabItem_left);
	}
	
	/**
	 * 设置中心右侧面板
	 */
	private void setShellMiddleRight(SashForm sashForm) {
		Composite composite_right = new Composite(sashForm, SWT.NONE);
		//水平填充
		composite_right.setLayout(new FillLayout(SWT.HORIZONTAL));
		//父窗体为右窗体 参数为一个有边框但没有标题栏的窗口
		tabFolder = new CTabFolder(composite_right, SWT.BORDER);
		//如果线程是图形界面线程，返回display实例。如果不是则返回的实例为空(getDefault是如果实例不存在则无视直接创建)
		tabFolder.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		//对右tabFolder添加get/set方法
		container = tabFolder;
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("主页");
		tabItem.setImage(SWTResourceManager.getImage("./icons/home.png"));
		//主页面板
		HomeComp homeComp = new HomeComp(tabFolder, SWT.NONE);
		tabItem.setControl(homeComp);
		formToolkit.paintBordersFor(homeComp);
		tabFolder.setSelection(tabItem);
	}
	
	/**
	 * 设置文本框的组件
	 * @param newComposite
	 * @param ylt
	 * @param haveLayoutData
	 * @return
	 */
	private Text setTextComposite(Composite newComposite, YummyLabelText ylt, boolean haveLayoutData) {
		//文本框组件
		Composite textComposite = new Composite(newComposite, SWT.NONE);
		//设置背景色
		textComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		textComposite.setLayout(ylt.getGridLayout());
		//控件是否设置独占一排
		if(haveLayoutData == true) {
			textComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 2, 1));
		}
		//将标签嵌于文本框组件中
		Label label = new Label(textComposite, SWT.NONE);
		label.computeSize(20, 20);
		label.setText(ylt.getLabelText());
		label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		//将输入框嵌入文本框组件中
		Text text = new Text(textComposite, SWT.BORDER);
		return text;
	}
	
	/**
	 * 设置管理按钮的组件
	 */
	private Composite setButtonComposite(Composite newComposite, HashMap<String, Text> text_map) {
		//按钮组件
		Composite buttonComposite = new Composite(newComposite, SWT.NONE);
		//设置背景色
		buttonComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		//预览
		Button button1 = new Button(buttonComposite, SWT.PUSH);
		button1.setData("id", "1");
		button1.setText("预览");
		button1.addSelectionListener(new ShellButtonListener());
		
		//保存参数
		Button button2 = new Button(buttonComposite, SWT.PUSH);
		button2.setData("id", "2");
		button2.setText("保存参数");
		button2.addSelectionListener(new ShellButtonListener());
		
		//点击保存参数按钮
		setWriteButton(buttonComposite, text_map);
		//点击读取参数按钮
		setReadButton(buttonComposite, text_map);
		
		buttonComposite.setLayout(new GridLayout(3, false));
		buttonComposite.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false, 2, 1));
		return buttonComposite;
	}
	
	/**
	 * 设置新开的窗口
	 * @param tabItem
	 * @return
	 */
	private Composite setNewComposite1(Shell shell) {
		Composite newComposite = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		//布局方式
		newComposite.setLayout(gridLayout);
		newComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		return newComposite;
	}
	
	/**
	 * 批量设置文本框组合
	 * @param newComposite
	 * @return
	 */
	private HashMap<String, Text> setTextInputPage(Composite newComposite) {
		YShell shell = new YShell();
		Map<String, List> word = shell.gettWord();
		Field[] f = shell.getClass().getDeclaredFields();
		HashMap<String, Text> text_map = new HashMap();
		for(int i = 0; i < word.size(); i++) {
			String text = f[i].getName();
			//设置英文字段与中文文本的映射
			List<String> tempList = word.get(text);
			if(!tempList.get(1).equals("1")) {
				continue;
			}
			//初始化文本框组合
			String labelText = tempList.get(0) + ":";
			YummyLabelText ylt = null;
			ylt = new YummyLabelText(new GridLayout(2, true), labelText);
			boolean haveLayoutData = false;
			if(word.size() % 2 == 1 && i == word.size() - 1) {
				haveLayoutData = true;
			} else {
				haveLayoutData = false;
			}
			//设置文本框组件
			Text out_text = setTextComposite(newComposite, ylt, haveLayoutData);
			//放入文本框组合的HashMap中
			text_map.put(f[i].getName(), out_text);
		}
		return text_map;
	}
	
	private void setWriteButton(Composite newComposite, final HashMap<String, Text> text_map) {
		Button button = new Button(newComposite, SWT.PUSH);
		button.setText("保存参数");
		button.addSelectionListener(new SelectionAdapter() {
			 //按钮监听器
			 public void widgetSelected(SelectionEvent e) { 
				 //读取默认文档配置(非保存的参数)
				 YShell in_shell = (YShell)RelateToFile.ReadingFile("K:/YummyParm/Shell/YShell");
				 Field[] fields = in_shell.getClass().getDeclaredFields();
				 for(int i = 0; i < fields.length; i++) {
					 String fieldsName = fields[i].getName();
					 if(!text_map.containsKey(fieldsName)) {
						 continue;
					 }
					 Text out_text = text_map.get(fieldsName);
					 if(out_text == null || out_text.getText() == null) {
						 continue;
					 }
					//取出参数值
			         String parm = out_text.getText();
					 AutoProducting.AutoMakeSetMethod(in_shell, fields, i, parm);
				 }
				/*
				 * 写入新弹出窗口的参数
				 */
				in_shell.setLayoutType("gridLayout");
				in_shell.setScCode("shell");
				in_shell.setColorCode(0);
				in_shell.setImagePath("./icons/(50).png");
				RelateToFile.WritingFile(in_shell, "K:/YummyData/" + in_shell.getScCode());
			 }
		});
	}
	
	private void setReadButton(Composite newComposite, final HashMap<String, Text> text_map) {
		Button button = new Button(newComposite, SWT.PUSH);
		button.setText("读取参数");
		button.addSelectionListener(new SelectionAdapter() {
			 //按钮监听器
			 public void widgetSelected(SelectionEvent e) { 
				 //读取文档配置
				 YShell in_shell = (YShell)RelateToFile.ReadingFile("K:/YummyData/Shell/YShell");
				 Field[] fields = in_shell.getClass().getDeclaredFields();
				 for(int i = 0; i < fields.length; i++) {
					 String fieldsName = fields[i].getName();
					 if(!text_map.containsKey(fieldsName)) {
						 continue;
					 }
					 Text out_text = text_map.get(fieldsName);
					 if(out_text == null) {
						 continue;
					 }
					 String parm = AutoProducting.autoMakeGetMethod(in_shell, fields, i);
					 out_text.setText(String.valueOf(parm));
				 }
			 }
		});
	}
}
