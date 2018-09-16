package cynynpri.java.sif.combose.application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import cynynpri.java.sif.combose.imshowstage.Imshow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GUIControler {
	@FXML private Tab devicesettingsTab;
	@FXML private Tab windowsettingsTab;
	@FXML private Tab detectsettingsTab;
	@FXML private ImageView mainIV;
	@FXML private Image showimg;
	@FXML private Button showwindowbtn;
	@FXML private Spinner<Integer> deviceIndex_spn;
	@FXML private Label statuslbl;
	private Stage ministage;
	private Imshow imshow;
	private ScheduledExecutorService timer;

	protected final void initializeComponents() {
		deviceIndex_spn = new Spinner<Integer>(0,10,0);
	}

	protected final void callimshow(Stage ownerStage) {
		this.imshow = new Imshow(ownerStage);
	}

	@FXML
	protected void shownewStage(MouseEvent event) {
		// TODO : definition stage show.
		try {
			this.imshow.setCapture((int)deviceIndex_spn.getValue());
			this.ministage = this.imshow.setImshowStage();
			this.timer = Executors.newSingleThreadScheduledExecutor();
			this.imshow.setTimer(this.timer);
			this.imshow.runTimer();
			this.ministage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
