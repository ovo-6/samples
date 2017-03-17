/*
    newUserHandler, editUserHandler, deleteUserHandler
*/
class Users extends React.Component{
    render() {
        var users = this.props.data.map(user =>
                <User key={user.name} data={user}
                    deleteHandler={this.props.deleteUserHandler}
                    editHandler={this.props.editUserHandler}/>
        );
        return (
            <div className="container">

            <ReactBootstrap.Button className="btn btn-info no-print" onClick={this.props.newUserHandler} >
                <ReactBootstrap.Glyphicon glyph="plus-sign" />
            </ReactBootstrap.Button>

            <ReactBootstrap.Table striped bordered condensed hover>
                <thead>
                <tr>
                    <th>User</th>
                    <th>Email</th>
                    <th>Roles</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {users}
                </tbody>
            </ReactBootstrap.Table>
            <button className="btn btn-info" onClick={this.props.prevPageHandler.bind(this)} disabled={this.props.prevDisabled}>Prev</button>
            &nbsp;Page {this.props.pageInfo.number+1} / {this.props.pageInfo.totalPages}&nbsp;
            <button className="btn btn-info" onClick={this.props.nextPageHandler.bind(this)} disabled={this.props.nextDisabled}>Next</button>
            </div>
        )
    }
}