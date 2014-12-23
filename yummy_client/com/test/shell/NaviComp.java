package com.test.shell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Shell左面板的设置
 * @author hengzai 时间：2013-8-2
 */

public class NaviComp extends Composite{
	//声明树形结构对象
	private TreeViewer treeViewer;
	
	//内部类 标签提供器 显示节点的相关信息
	private static class ViewerLabelProvider extends LabelProvider {
		//得到节点的显示图标
		public Image getImage(Object obj) {
			String imageKey = "./icons/two.png";
			Element element = (Element)obj;
			if(element.getName().equals("node")) {
				imageKey = "./icons/three.png";
			}
			return SWTResourceManager.getImage(imageKey);
		}
		
		//得到此节点的显示文本
		@Override
		public String getText(Object obj) {
			//强转 该Element是dom4j的元素节点类 dom4j用于读写xml文件
			Element element = (Element)obj;
			//获得节点内容
			String text = element.attributeValue("name");
			return text;
		}
	}
	
	//继承内容提供器接口 内部类
	private static class TreeContentProvider implements ITreeContentProvider {
		
		//树销毁时被调用
		@Override
		public void dispose() {
		}
		
		//输入改变时调用此方法
		@Override
		public void inputChanged(Viewer viewer, 
				Object oldInput, Object newInput) {
		
		}
		
		//当用户选择节点打开子节点时会调用此方法返回下一层子节点
		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof Element) {
				//elements取出里面的元素(List<Element>)并变成数组
				return ((Element)parentElement).elements().toArray(
						new Object[]{});
			}
			return new Object[0];
		}
		
		//程序开始构建时首先调用此方法返回一个对象的数组
		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}
		
		//将树的第一层节点显示出来
		@Override
		public Object getParent(Object child) {
			if(child instanceof Element) {
				return ((Element)child).getParent();
			}
			return null;
		}
		
		//判断该节点是否含有子节点
		@Override
		public boolean hasChildren(Object parent) {
			if(parent instanceof Element) {
				//返回节点数是否大于0的结果
				return ((Element)parent).nodeCount() > 0;
			}
			return false;
		}
	}
	
	//构造方法 面板容器Composite
	public NaviComp(Composite parent, int style) {
		super(parent, style);
		//设置布局 水平填充
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		treeViewer = new TreeViewer(this, SWT.NONE);
		treeViewer.setLabelProvider(new ViewerLabelProvider());
		treeViewer.setContentProvider(new TreeContentProvider());
		//放入所属内容
		treeViewer.setInput(createDummyModel());
		
//		Test test = new Test();
//		Element node = test.testing();
//		treeViewer.setInput(node);
		
		//展开树
		treeViewer.expandAll();
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
	
	/**
	 * 读取文件内容并转化成Element对象
	 * @return
	 */
	private Element createDummyModel() {
		//初始化参数
		Element root = null;
		try {
			//XML解析器
			SAXReader reader = new SAXReader();
			//文件输入流 读取该工程目录下的xml文件
			FileInputStream fs = new FileInputStream("./menu.xml");
			//声明一个xml文档
			Document doc = reader.read(fs);
			//获得根元素
			root = doc.getRootElement();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}
	
	//防止出现Subclassing not allowed异常
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
