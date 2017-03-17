/*
    show, submitHandler, onHide, errorText, editObject
*/
class NewExpenseDialog extends React.Component{

     constructor(props) {
        super(props);
        this.state = {description: '', amount: ''};
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        var returnObj = {};
        returnObj[event.target.id] = event.target.value;
        this.setState(returnObj);
    }

    validateNotEmpty(value) {
        const length = value.length;
        var r = 'error';
        if (length > 0) r = 'success';
        return r;
    }

    validateNumber(value) {
        return isNaN(parseFloat(value))? 'error': 'success';
    }

    render() {
        var inputProps = {id: 'datetime'};

        var editObject = this.props.editObject;
        var date = (editObject==null)? new Date(): new Date(editObject.datetime);
        var description = (editObject==null)? '': editObject.description;
        var amount = (editObject==null)? '': editObject.amount;
        var comment = (editObject==null)? '': editObject.comment;
        var existingExpense = (editObject==null)? '': editObject._links.self.href;

        var title = (editObject==null)? 'New Expense': 'Edit Expense';

        return (
            <ReactBootstrap.Modal show={this.props.show} onHide={this.props.onHide} bsSize="small">
              <ReactBootstrap.Modal.Header closeButton>
                <ReactBootstrap.Modal.Title>{title}</ReactBootstrap.Modal.Title>
              </ReactBootstrap.Modal.Header>
              <ReactBootstrap.Modal.Body>
                <form onSubmit={this.props.submitHandler}>
                    <input type="hidden" id="existingExpense" value={existingExpense} />
                    <ReactBootstrap.FormGroup>

                        <Datetime inputProps={inputProps} closeOnSelect defaultValue={date}/>

                    </ReactBootstrap.FormGroup>

                    <ReactBootstrap.FormGroup validationState={this.validateNotEmpty(this.state.description)}>
                          <ReactBootstrap.FormControl type="text" id="description" placeholder="Description" defaultValue={description} onChange={this.handleChange} />
                          <ReactBootstrap.FormControl.Feedback />
                    </ReactBootstrap.FormGroup>

                    <ReactBootstrap.FormGroup validationState={this.validateNumber(this.state.amount)}>
                          <ReactBootstrap.FormControl type="text" id="amount" placeholder="Amount" defaultValue={amount} onChange={this.handleChange} />
                          <ReactBootstrap.FormControl.Feedback />
                    </ReactBootstrap.FormGroup>

                    <ReactBootstrap.FormGroup>
                          <ReactBootstrap.FormControl componentClass="textarea" defaultValue={comment}
                                id="comment" placeholder="Optional Comment" />
                    </ReactBootstrap.FormGroup>

                      <ReactBootstrap.Button
                        className="btn btn-primary btn-large centerButton"
                        type="submit"
                         disabled={
                            this.validateNotEmpty(this.state.description)=='error' ||
                            this.validateNumber(this.state.amount)=='error'
                        }
                      >Save</ReactBootstrap.Button>
                 </form>
                 <p>{this.props.errorText}</p>
              </ReactBootstrap.Modal.Body>
            </ReactBootstrap.Modal>
        )
    }
}