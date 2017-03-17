/*
    <User key={} data={} deleteHandler={}/>
*/
class User extends React.Component{

    render() {
        return (
            <tr>
                <td>{this.props.data.name}</td>
                <td>{this.props.data.email}</td>
                <td>{this.props.data.roles}</td>
                <td>
                    <ReactBootstrap.Button className="no-print" onClick={this.props.editHandler.bind(this, this.props.data)}>
                        <ReactBootstrap.Glyphicon glyph="edit" />
                    </ReactBootstrap.Button>

                    <ReactBootstrap.Button onClick={this.props.deleteHandler.bind(this, this.props.data.name, this.props.data._links.self.href)}>
                        <ReactBootstrap.Glyphicon glyph="trash" />
                    </ReactBootstrap.Button>
                </td>
            </tr>
        )
    }
}
