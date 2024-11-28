package fightGame.view.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
/**
 * Custom cell renderer for the game board table that displays a list of icons within each cell.
 */
public class CellRenderer extends DefaultTableCellRenderer {

    /**
     * Customizes the rendering of each cell to display a list of icons.
     *
     * @param table     the JTable being rendered
     * @param value     the value to assign to the cell (expected to be a list of {@link ImageIcon})
     * @param isSelected whether the cell is selected
     * @param hasFocus   whether the cell has focus
     * @param row       the row of the cell
     * @param column    the column of the cell
     * @return the component used for rendering the cell
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        @SuppressWarnings("unchecked")
        List<ImageIcon> iconList = (List<ImageIcon>) value;

        int cellWidth = table.getColumnModel().getColumn(column).getWidth();
        int cellHeight = table.getRowHeight(row);

        int iconCount = iconList.size();
        int spacing = 1; // Space between icons

        int maxWidth = (cellWidth - (iconCount - 1) * spacing) / iconCount;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int x = 0; // Horizontal position for icons
                for (ImageIcon icon : iconList) {
                    // Resize icon to fit within the available space
                    int iconWidth = Math.min(maxWidth, icon.getIconWidth());
                    int iconHeight = Math.min(cellHeight, icon.getIconHeight());
                    int y = (cellHeight - iconHeight) / 2; // Center vertically

                    g.drawImage(icon.getImage(), x, y, iconWidth, iconHeight, this);
                    x += iconWidth + spacing; // Move to the next icon position
                }

                // Add selection effect
                if (isSelected) {
                    g.setColor(new Color(0, 0, 0, 100)); // Semi-transparent overlay
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add cell border
        return panel;
    }
}
