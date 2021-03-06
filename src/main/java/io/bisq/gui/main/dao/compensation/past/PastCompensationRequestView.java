/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bisq.gui.main.dao.compensation.past;

import io.bisq.common.locale.Res;
import io.bisq.core.btc.wallet.BsqWalletService;
import io.bisq.core.dao.DaoPeriodService;
import io.bisq.core.dao.blockchain.BsqBlockChain;
import io.bisq.core.dao.blockchain.BsqBlockChainChangeDispatcher;
import io.bisq.core.dao.blockchain.BsqBlockChainListener;
import io.bisq.core.dao.request.compensation.CompensationRequestManager;
import io.bisq.gui.common.view.FxmlView;
import io.bisq.gui.components.SeparatedPhaseBars;
import io.bisq.gui.main.dao.compensation.CompensationRequestDisplay;
import io.bisq.gui.main.dao.compensation.CompensationRequestView;
import io.bisq.gui.util.BsqFormatter;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;
import java.util.List;

@FxmlView
public class PastCompensationRequestView extends CompensationRequestView implements BsqBlockChainListener {

    private List<SeparatedPhaseBars.SeparatedPhaseBarsItem> phaseBarsItems;


    ///////////////////////////////////////////////////////////////////////////////////////////
    // Constructor, lifecycle
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Inject
    private PastCompensationRequestView(CompensationRequestManager compensationRequestManger,
                                        DaoPeriodService daoPeriodService,
                                        BsqWalletService bsqWalletService,
                                        BsqBlockChain bsqBlockChain,
                                        BsqBlockChainChangeDispatcher bsqBlockChainChangeDispatcher,
                                        BsqFormatter bsqFormatter) {
        super(compensationRequestManger, bsqWalletService, bsqBlockChain, bsqBlockChainChangeDispatcher, bsqFormatter);
    }

    @Override
    public void initialize() {
        root.getStyleClass().add("compensation-root");
        AnchorPane topAnchorPane = new AnchorPane();
        root.getChildren().add(topAnchorPane);

        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        AnchorPane.setBottomAnchor(gridPane, 10d);
        AnchorPane.setRightAnchor(gridPane, 10d);
        AnchorPane.setLeftAnchor(gridPane, 10d);
        AnchorPane.setTopAnchor(gridPane, 0d);
        topAnchorPane.getChildren().add(gridPane);

        // Add compensationrequest pane
        tableView = new TableView<>();
        detailsGridPane = new GridPane();
        compensationRequestDisplay = new CompensationRequestDisplay(detailsGridPane, bsqFormatter, bsqWalletService, null);
        compensationRequestPane = compensationRequestDisplay.createCompensationRequestPane(tableView, Res.get("dao.compensation.past.header"));
        compensationRequestPane.setMinWidth(800);
        GridPane.setColumnSpan(compensationRequestPane, 2);
        GridPane.setColumnIndex(compensationRequestPane, 0);
        GridPane.setMargin(compensationRequestPane, new Insets(0, -10, 0, -10));
        GridPane.setRowIndex(compensationRequestPane, gridRow);

        gridPane.getChildren().add(compensationRequestPane);

        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

        compensationRequestListChangeListener = c -> updateList();
        chainHeightChangeListener = (observable, oldValue, newValue) -> {
            updateList();
        };
    }

    @Override
    protected void updateList() {
        doUpdateList(compensationRequestManger.getPastRequests());
    }
}

