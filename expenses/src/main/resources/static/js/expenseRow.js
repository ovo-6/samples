/*
    Row in expenses table.
    Usage:
        <ExpenseRow key={} data={} editHandler={} deleteHandler={} showOwner={}/>

*/
class ExpenseRow extends React.Component{

    render() {
        var ownerColumn = null;
        if (this.props.showOwner) {
            ownerColumn = <td>{this.props.data.owner}</td>;
        }

        return (
            <tr>
                <td>{new Date(this.props.data.datetime).toLocaleString()}</td>
                {ownerColumn}
                <td>
                    <b>{this.props.data.description}</b><br/>
                    <small>{this.props.data.comment}</small>
                </td>
                <td>$ {this.props.data.amount}</td>
                <td className="no-print">
                    <ReactBootstrap.Button className="no-print" onClick={this.props.editHandler.bind(this, this.props.data)}>
                        <ReactBootstrap.Glyphicon glyph="edit" />
                    </ReactBootstrap.Button>

                    <ReactBootstrap.Button className="no-print" onClick={this.props.deleteHandler.bind(this, this.props.data.description, this.props.data._links.self.href)}>
                        <ReactBootstrap.Glyphicon glyph="trash" />
                    </ReactBootstrap.Button>
                </td>
            </tr>
        )
    }
}
