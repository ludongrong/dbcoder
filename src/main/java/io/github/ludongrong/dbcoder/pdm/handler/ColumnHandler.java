package io.github.ludongrong.dbcoder.pdm.handler;

import java.util.ArrayList;
import java.util.List;

import io.github.ludongrong.dbcoder.util.DomUtil;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import io.github.ludongrong.dbcoder.pdm.Column;
import io.github.ludongrong.dbcoder.pdm.Table;
import lombok.Getter;

/**
 * ColumnHandler
 *
 * <pre>
 *   <o:Column Id="o52">
 *     <a:ObjectID>A870782D-AC9D-4E93-9855-9B9EA4352523</a:ObjectID>
 *     <a:Name>ID</a:Name>
 *     <a:Code>ID</a:Code>
 *     <a:CreationDate>1354192135</a:CreationDate>
 *     <a:Creator>Don.ron</a:Creator>
 *     <a:ModificationDate>1525679048</a:ModificationDate>
 *     <a:Modifier>Don.ron</a:Modifier>
 *     <a:DataType>varchar(32)</a:DataType>
 *     <a:Length>32</a:Length>
 *     <a:Column.Mandatory>1</a:Column.Mandatory>
 *     <c:Domain>
 *       <o:PhysicalDomain Ref="o21"/>
 *     </c:Domain>
 *   </o:Column>
 * </pre>
 * 
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-23
 */
public class ColumnHandler implements ElementHandler {

	@Getter
	List<Column> columnList = new ArrayList<Column>();

	Table table;

	public ColumnHandler(Table table) {
		this.table = table;
	}

	public void onStart(ElementPath path) {
	}

	public void onEnd(ElementPath path) {
		Element element = path.getCurrent();

		Column column = new Column();
		column.setId(element.attributeValue("Id"));
		column.setName(DomUtil.getText(element, "Name"));
		column.setCode(DomUtil.getText(element, "Code"));
		column.setDataType(DomUtil.getText(element, "DataType"));
		column.setLength(DomUtil.getInt(element, "Length"));
		column.setPrecision(DomUtil.getInt(element, "Precision"));
		column.setMandatory(DomUtil.getBoolean(element, "Column.Mandatory"));
		column.setTable(table);
		columnList.add(column);

		element.detach();
	}
}
