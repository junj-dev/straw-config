import Vue from '../../utils/vue';
import { htmlOrText } from '../../utils/html';
import formOptionsMixin from '../../mixins/form-options';
import normalizeSlotMixin from '../../mixins/normalize-slot'; // @vue/component

export var BFormDatalist = /*#__PURE__*/Vue.extend({
  name: 'BFormDatalist',
  mixins: [formOptionsMixin, normalizeSlotMixin],
  props: {
    id: {
      type: String,
      required: true
    }
  },
  render: function render(h) {
    var $options = this.formOptions.map(function (option, index) {
      var value = option.value,
          text = option.text,
          html = option.html,
          disabled = option.disabled;
      return h('option', {
        attrs: {
          value: value,
          disabled: disabled
        },
        domProps: htmlOrText(html, text),
        key: "option_".concat(index)
      });
    });
    return h('datalist', {
      attrs: {
        id: this.id
      }
    }, [$options, this.normalizeSlot('default')]);
  }
});