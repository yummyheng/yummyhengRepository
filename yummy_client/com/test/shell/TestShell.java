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
 * �����������
 * ��ע:��Ҫjar��org.eclipse.equinox.common
 * @author hengzai ʱ�䣺2013-8-2
 */
public class TestShell extends Shell{
	//���幤�߰�
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	//������������
	private NaviComp naviComp;
	//��������ѡ����������������������
	private CTabFolder tabFolder;
	private static CTabFolder container;
	//ѡ����ݵĹ�ϣ������
	public static HashMap<String, CTabItem> itemMap = new HashMap<String, CTabItem>();
	
	
	public static void main(String args[]) {
		//����������򴴽���ʵ��
		Display display = Display.getDefault();
		//��Ҫ���캯��
		TestShell shell = new TestShell(display);
		//�򿪽���
		shell.open();
		//shellӦ�ý�������
		shell.layout();
		//��Ϣѭ�� �������һֱ������
		while(!shell.isDisposed()) {
			//����������Ķ�ȡ�ͷ���
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	//���췽��
	public TestShell(Display display) {
		//shell��Ĭ�ϲ��� �ӽ��ڴ������ʽ���
		super(display, SWT.SHELL_TRIM);
		//������ҳ
		setMainPage();
		//��������
		createContents();
	}
	
	//��ֹ����Subclassing not allowed�쳣
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	protected void createContents() {
		//���ñ�����ı�:���еĳ���  unicode����
		setText("\u6052\u4ed4\u7684\u7a0b\u5e8f");
		//�����ܴ��ڴ�С
		setSize(900,700);
		//�����ڲ��� ����Ļ���  ��DOM�д���ISelectionChangedListener
		naviComp.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {
		//ѡ���ѡ�ļ�����
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			//ѡ����
			StructuredSelection selection = (StructuredSelection) event.getSelection();
			//����xmlԪ��
			Element element = (Element) selection.getFirstElement();
			final String uid = element.attributeValue("uid");
			String title = element.attributeValue("name");
			//�����������
			if(uid == null || uid.trim().equals("")) {
				return;
			}
			//uid��ΪHashMap��key
			CTabItem tabItem = itemMap.get(uid);
			//ʵ��Ϊ��ʱ����
			if(tabItem == null) {
				//����ֻ�йرհ�ť�Ĵ���ǩ���ݿؼ�
				tabItem = new CTabItem(tabFolder,SWT.CLOSE);
				
				/*
				 * �����
				 */
				tabItem.setText("�����µĵ���");
				tabItem.setImage(SWTResourceManager.getImage("./icons/three.png"));
				
				//����ӵ������ʼ��
				Composite newComposite = setNewComposite(tabItem);
				//�����������
				HashMap<String, Text> text_map = setTextInputPage(newComposite);
				setButtonComposite(newComposite, text_map);
				newComposite.layout();
				formToolkit.paintBordersFor(newComposite);
				itemMap.put(uid, tabItem);
				//��������������ٵļ�����
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
	 * ���ô��ڲ���
	 */
	private void setLayout4main() {
		//���񲼾ֲ���:���� �Ƿ�ȷ־���
		GridLayout gl = new GridLayout(1, false);
		gl.verticalSpacing = 0;
		//�������ݺͱ߽����
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		//���ò���
		setLayout(gl);
	}
	
	/**
	 * ���ô���ͷ��
	 */
	private void setShellHead() {
		//������� ����������壬��� SWT.NONEĬ��ʽ��
		Composite composite_top = new Composite(this, SWT.NONE);
		//���ñ�����ɫ
		composite_top.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		//�̳и����ģʽ
		composite_top.setBackgroundMode(SWT.INHERIT_FORCE);
		//�������񲼾� ������3��(3�����a1 a2 a3)�����ȿ�
		GridLayout gl_composite_top = new GridLayout(3, false);
		gl_composite_top.marginWidth = 0;
		gl_composite_top.marginHeight = 0;
		//������� ����Ϊ���ڲ��ֵ���ʽ ��������� �������
		composite_top.setLayout(gl_composite_top);
		//�½����������ಢ�������� ���н����๲�в���:ˮƽ��䡢��ֱ���С��ܷ��˹�����(ˮƽ�ɡ���ֱ��) ����ռ������(ˮƽ1�� ��ֱ1)
		composite_top.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		//���ñ�ǩͷ a1
		Label label = new Label(composite_top, SWT.NONE);
		label.setImage(SWTResourceManager.getImage("./icons/label.png"));
		
		//���������� a2 �����հ� ��λ��
		Composite composite_3 = new Composite(composite_top, SWT.NONE);
		composite_3.setLayout(new GridLayout(1, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1, 1));
		
		//�ų����ӵ���� a3 ������
		Composite composite_4 = new Composite(composite_top, SWT.NONE);
		composite_4.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		composite_4.setLayout(new GridLayout(4, false));
		//���ó����� ��a3��
		Link link_1 = new Link(composite_4, SWT.NONE);
		formToolkit.adapt(link_1, true, true);
		//unicode����  ��ҳ
		link_1.setText("<a>\u4E3B\u9875</a>");
	}
	
	/**
	 * ���ô��ڵ����Ĳ���
	 */
	private void setShellMiddle() {
		//�ָ������
		SashForm sashForm = new SashForm(this, SWT.NONE); 
		//���ָ�ļ��
		sashForm.setSashWidth(5);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1, 1));
		//���������
		setShellMiddleLeft(sashForm);
		//���������
		setShellMiddleRight(sashForm);
		//�ָ��ָ����
		sashForm.setWeights(new int[] {2, 8});
	}
	
	/**
	 * ���ô�����ҳ
	 */
	private void setMainPage() {
		//���ڶ��������
		setImage(SWTResourceManager.getImage(Constants4Yummy.SHELL_MAIN_TOPIC_ICON1));
		//�������岼�ֲ���
		setLayout4main();
		//�趨ͷ��
		setShellHead();
		//�趨���Ĳ���
		setShellMiddle();
	}
	
	/**
	 * �����¿��Ĵ���
	 * @param tabItem
	 * @return
	 */
	private Composite setNewComposite(CTabItem tabItem) {
		Composite newComposite = new Composite(tabFolder, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		//���ַ�ʽ
		newComposite.setLayout(gridLayout);
		newComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		//�����¼�
		tabItem.setControl(newComposite);
		return newComposite;
	}
	
	/**
	 * ��������������
	 */
	private void setShellMiddleLeft(SashForm sashForm) {
		Composite composite_left = new Composite(sashForm, SWT.NONE);
		//ˮƽ���
		composite_left.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		//��������ѡ���������������� SWT.BORDER һ���б߿�û�б������Ĵ���
		CTabFolder tabFolder_left = new CTabFolder(composite_left, SWT.BORDER);
		formToolkit.adapt(tabFolder_left);
		//���ñ߿�
		formToolkit.paintBordersFor(tabFolder_left);
		//������ƽ̨��������ɫ�����Ʋ��н���Ч��
		tabFolder_left.setSelectionBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		//ѡ� ���ڷ���ѡ���������
		CTabItem tabItem_left = new CTabItem(tabFolder_left, SWT.NONE);
		//unicode ������
		tabItem_left.setText("\u5BFC\u822A\u680F");
		//����ͼ��
		tabItem_left.setImage(SWTResourceManager.getImage("./icons/navi.png"));
		
		naviComp = new NaviComp(tabFolder_left, SWT.NONE);
		
		//����������¼�
		tabItem_left.setControl(naviComp);
		formToolkit.paintBordersFor(naviComp);
		tabFolder_left.setSelection(tabItem_left);
	}
	
	/**
	 * ���������Ҳ����
	 */
	private void setShellMiddleRight(SashForm sashForm) {
		Composite composite_right = new Composite(sashForm, SWT.NONE);
		//ˮƽ���
		composite_right.setLayout(new FillLayout(SWT.HORIZONTAL));
		//������Ϊ�Ҵ��� ����Ϊһ���б߿�û�б������Ĵ���
		tabFolder = new CTabFolder(composite_right, SWT.BORDER);
		//����߳���ͼ�ν����̣߳�����displayʵ������������򷵻ص�ʵ��Ϊ��(getDefault�����ʵ��������������ֱ�Ӵ���)
		tabFolder.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		//����tabFolder���get/set����
		container = tabFolder;
		
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("��ҳ");
		tabItem.setImage(SWTResourceManager.getImage("./icons/home.png"));
		//��ҳ���
		HomeComp homeComp = new HomeComp(tabFolder, SWT.NONE);
		tabItem.setControl(homeComp);
		formToolkit.paintBordersFor(homeComp);
		tabFolder.setSelection(tabItem);
	}
	
	/**
	 * �����ı�������
	 * @param newComposite
	 * @param ylt
	 * @param haveLayoutData
	 * @return
	 */
	private Text setTextComposite(Composite newComposite, YummyLabelText ylt, boolean haveLayoutData) {
		//�ı������
		Composite textComposite = new Composite(newComposite, SWT.NONE);
		//���ñ���ɫ
		textComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		textComposite.setLayout(ylt.getGridLayout());
		//�ؼ��Ƿ����ö�ռһ��
		if(haveLayoutData == true) {
			textComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 2, 1));
		}
		//����ǩǶ���ı��������
		Label label = new Label(textComposite, SWT.NONE);
		label.computeSize(20, 20);
		label.setText(ylt.getLabelText());
		label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		//�������Ƕ���ı��������
		Text text = new Text(textComposite, SWT.BORDER);
		return text;
	}
	
	/**
	 * ���ù���ť�����
	 */
	private Composite setButtonComposite(Composite newComposite, HashMap<String, Text> text_map) {
		//��ť���
		Composite buttonComposite = new Composite(newComposite, SWT.NONE);
		//���ñ���ɫ
		buttonComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		
		//Ԥ��
		Button button1 = new Button(buttonComposite, SWT.PUSH);
		button1.setData("id", "1");
		button1.setText("Ԥ��");
		button1.addSelectionListener(new ShellButtonListener());
		
		//�������
		Button button2 = new Button(buttonComposite, SWT.PUSH);
		button2.setData("id", "2");
		button2.setText("�������");
		button2.addSelectionListener(new ShellButtonListener());
		
		//������������ť
		setWriteButton(buttonComposite, text_map);
		//�����ȡ������ť
		setReadButton(buttonComposite, text_map);
		
		buttonComposite.setLayout(new GridLayout(3, false));
		buttonComposite.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false, false, 2, 1));
		return buttonComposite;
	}
	
	/**
	 * �����¿��Ĵ���
	 * @param tabItem
	 * @return
	 */
	private Composite setNewComposite1(Shell shell) {
		Composite newComposite = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		//���ַ�ʽ
		newComposite.setLayout(gridLayout);
		newComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		return newComposite;
	}
	
	/**
	 * ���������ı������
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
			//����Ӣ���ֶ��������ı���ӳ��
			List<String> tempList = word.get(text);
			if(!tempList.get(1).equals("1")) {
				continue;
			}
			//��ʼ���ı������
			String labelText = tempList.get(0) + ":";
			YummyLabelText ylt = null;
			ylt = new YummyLabelText(new GridLayout(2, true), labelText);
			boolean haveLayoutData = false;
			if(word.size() % 2 == 1 && i == word.size() - 1) {
				haveLayoutData = true;
			} else {
				haveLayoutData = false;
			}
			//�����ı������
			Text out_text = setTextComposite(newComposite, ylt, haveLayoutData);
			//�����ı�����ϵ�HashMap��
			text_map.put(f[i].getName(), out_text);
		}
		return text_map;
	}
	
	private void setWriteButton(Composite newComposite, final HashMap<String, Text> text_map) {
		Button button = new Button(newComposite, SWT.PUSH);
		button.setText("�������");
		button.addSelectionListener(new SelectionAdapter() {
			 //��ť������
			 public void widgetSelected(SelectionEvent e) { 
				 //��ȡĬ���ĵ�����(�Ǳ���Ĳ���)
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
					//ȡ������ֵ
			         String parm = out_text.getText();
					 AutoProducting.AutoMakeSetMethod(in_shell, fields, i, parm);
				 }
				/*
				 * д���µ������ڵĲ���
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
		button.setText("��ȡ����");
		button.addSelectionListener(new SelectionAdapter() {
			 //��ť������
			 public void widgetSelected(SelectionEvent e) { 
				 //��ȡ�ĵ�����
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
