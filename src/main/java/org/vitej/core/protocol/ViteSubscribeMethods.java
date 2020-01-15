package org.vitej.core.protocol;

import io.reactivex.Flowable;
import org.vitej.core.protocol.methods.Address;
import org.vitej.core.protocol.methods.request.VmLogFilter;
import org.vitej.core.protocol.methods.response.*;

/**
 * Go-vite subscribe API
 */
public interface ViteSubscribeMethods {
    /**
     * Start listening for new snapshot blocks
     *
     * @return Snapshot blocks dataflow
     */
    Flowable<SnapshotBlockNotification> snapshotBlockFlowable();

    /**
     * Start listening for new account blocks.
     *
     * @return Account blocks dataflow of all accounts
     */
    Flowable<AccountBlockNotification> accountBlockFlowable();

    /**
     * Start listening for new account blocks on specified account.
     *
     * @param address Address of account
     * @return Account blocks dataflow of specified account
     */
    Flowable<AccountBlockWithHeightNotification> accountBlockByAddressFlowable(Address address);

    /**
     * Start listening for unreceived transactions on specified account, including new unreceived
     * transactions and newly received transactions
     *
     * @param address Address of account
     * @return Account blocks unreceive and receive event dataflow of specified account.
     */
    Flowable<UnreceivedBlockNotification> unreceivedBlockFlowable(Address address);

    /**
     * Start listening for new logs.
     *
     * @param filter Filter param
     * @return Vmlog dataflow matches the specified filter param
     */
    Flowable<VmlogNotification> vmlogFlowable(VmLogFilter filter);
}
