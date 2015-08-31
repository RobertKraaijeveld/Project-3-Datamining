package Calendar;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.util.Properties;

/**
 * Created by jls on 2/13/15.
 */
public class DatePicker {
    private UtilDateModel model;
    private Properties p;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    public DatePicker() {
        createDatePicker();
    }

    private void createDatePicker() {
        model = new UtilDateModel();
        p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    public String getSelectedDate() {
        String date = (String) getDatePicker().getModel().getValue();
        return date;
    }

    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }
}
