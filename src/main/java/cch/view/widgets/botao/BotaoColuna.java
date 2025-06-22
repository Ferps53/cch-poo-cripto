package cch.view.widgets.botao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class BotaoColuna extends AbstractCellEditor
    implements TableCellEditor, TableCellRenderer, ActionListener {

  private final JTable table;
  private final transient Action action;
  private transient Object editorValue;
  private final JButton editorButton;

  public BotaoColuna(JTable table, Action action, int coluna, JButton botao) {

    this.table = table;
    this.action = action;
    this.editorButton = botao;

    TableColumnModel model = table.getColumnModel();
    editorButton.addActionListener(this);
    model.getColumn(coluna).setCellRenderer(this);
    model.getColumn(coluna).setCellEditor(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    int row = table.convertRowIndexToModel(table.getEditingRow());
    fireEditingStopped();

    final var event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, String.valueOf(row));
    action.actionPerformed(event);
  }

  @Override
  public Component getTableCellEditorComponent(
      JTable table, Object value, boolean isSelected, int row, int column) {

    if (value == null) {
      editorButton.setText("");
      editorButton.setIcon(null);
    } else if (value instanceof Icon icon) {
      editorButton.setText("");
      editorButton.setIcon(icon);
    } else {
      editorButton.setText(value.toString());
    }
    this.editorValue = value;
    return editorButton;
  }

  @Override
  public Object getCellEditorValue() {
    return editorValue;
  }

  @Override
  public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    return getTableCellEditorComponent(table, value, isSelected, row, column);
  }
}
