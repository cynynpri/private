package cynynpri.java.sif.combose.imshowstage;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cynynpri.java.sif.combose.imgproc.CaptureImg;
import cynynpri.java.sif.combose.imgproc.OpenCVCapture;
import cynynpri.java.sif.combose.imgproc.OpenCVException;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Imshow{
	private Stage imshowGui;
	private Stage ownerStage;
	private ImageView imgView;
	private OpenCVCapture capture;
	private CaptureImg src_img;
	private ScheduledExecutorService timer;

	public Imshow(Stage ownerStage) {
		this.ownerStage = ownerStage;
	}

	public final void setOwner(Stage primaryStage) {
		this.ownerStage = primaryStage;
	}

	public final void setCapture(int deviceIndex) {
		this.capture = new OpenCVCapture(deviceIndex);
	}

	public final void setTimer(ScheduledExecutorService timer) {
		this.timer = timer;
	}

	public final void runTimer() {
		this.timer.scheduleAtFixedRate(getImgloop(), 0, (int)Math.floor(1000.0/30.0), TimeUnit.MILLISECONDS);
	}

	public final void stopTimer() {
		try {
			this.timer.shutdown();
			this.timer.awaitTermination((int)Math.floor(1000.0/30.0), TimeUnit.MILLISECONDS);
			ImgloopStop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final Runnable getImgloop() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					if(null == capture) {
						String err = new String();
						err = "capture is null. NullPointerException";
						System.err.println(err);
						throw new OpenCVException(-2, err);
					}
					src_img = capture.captureRun();
					if(null == src_img) {
						String err = new String();
						err = "capture error:caused by setFrame";
						System.err.println(err);
						capture.stop();
						throw new OpenCVException(-3, err);
					}
					Image img = src_img.getImg();
					imgView.imageProperty().setValue(img);
				}catch(OpenCVException err) {
					err.printStackTrace();
				}
			}
		};
	}

	public final Stage setImshowStage() {
		imshowGui = new Stage();
		imshowGui.initModality(Modality.APPLICATION_MODAL);
		imshowGui.initOwner(ownerStage);

		imgView = new ImageView();
		BorderPane root = new BorderPane();
		root.setCenter(imgView);
		// TODO : CaptureImg to setWidth, setHeight settings.
		Scene scene = new Scene(root);
		imshowGui.setScene(scene);

		EventHandler<WindowEvent> closeHandler = imshowGui.getOnCloseRequest();
		imshowGui.setOnCloseRequest(e -> {
			stopTimer();
			if(closeHandler != null) {
				closeHandler.handle(e);
			}
		});

		imgView.setFocusTraversable(true);
		imgView.setOnKeyPressed(setWaitKey());

		return imshowGui;
	}

	private final EventHandler<KeyEvent> setWaitKey() {
		return new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				setKeyEvents(event);
			}
		};
	}

	private final void setKeyEvents(KeyEvent event) {
		switch(event.getCode()) {
		case Q :
			stopTimer();
			imshowGui.getScene().getWindow().hide();
		default :
			break;
		}
	}

	public final int getFps() {
		return this.capture.getFps();
	}

	private final void ImgloopStop() {
		this.capture.stop();
	}
}
