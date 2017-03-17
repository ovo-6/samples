/*
    Expenses table.
    Usage:
        <Expenses
            data={}
            nextPageHandler={}
            prevPageHandler={}
            prevDisabled={}
            nextDisabled={}
            editExpenseHandler={}
            deleteExpenseHandler={}
            pageInfo={}
            totalAmount={}
            averageAmount={}
            showOwner={}
         />

*/
class Expenses extends React.Component{
    render() {
        var ownerTh = null;
        var ownerCol = null;
        if (this.props.showOwner) {
            ownerTh = <th>Owner</th>;
             ownerCol = <col />;
        }

        var users = this.props.data.map(expense =>
                <ExpenseRow
                    key={expense._links.self.href} data={expense}
                    editHandler={this.props.editExpenseHandler}
                    deleteHandler={this.props.deleteExpenseHandler}
                    showOwner={this.props.showOwner}
                    />
        );
        return (
            <div className="container">
            <ReactBootstrap.Table striped bordered condensed hover>

                <colgroup>
                    <col style={{width: '13em'}}/>
                    {ownerCol}
                    <col />
                    <col />
                    <col style={{width: '7em'}} className="no-print"/>
                </colgroup>

                <thead>
                <tr>
                    <th>When</th>
                    {ownerTh}
                    <th>What</th>
                    <th>Amount</th>
                    <th className="no-print"></th>
                </tr>
                </thead>
                <tbody>
                {users}
                </tbody>
            </ReactBootstrap.Table>
            <button className="btn btn-info no-print" onClick={this.props.prevPageHandler.bind(this)} disabled={this.props.prevDisabled}>Prev</button>
            &nbsp;Page {this.props.pageInfo.number+1} / {this.props.pageInfo.totalPages}&nbsp;
            <button className="btn btn-info no-print" onClick={this.props.nextPageHandler.bind(this)} disabled={this.props.nextDisabled}>Next</button>
            &nbsp;&nbsp;
            <span><b>Total sum:</b> $ {this.props.totalAmount}, </span> <span><b>Average:</b> $ {this.props.averageAmount}</span> (for all pages)
            </div>
        )
    }
}