import { htmlOrText } from '../../../utils/html';
import { isFunction } from '../../../utils/inspect';
import { BTr } from '../tr';
import { BTd } from '../td';
export default {
  props: {
    showEmpty: {
      type: Boolean,
      default: false
    },
    emptyText: {
      type: String,
      default: 'There are no records to show'
    },
    emptyHtml: {
      type: String
    },
    emptyFilteredText: {
      type: String,
      default: 'There are no records matching your request'
    },
    emptyFilteredHtml: {
      type: String
    }
  },
  methods: {
    renderEmpty: function renderEmpty() {
      var h = this.$createElement;
      var items = this.computedItems;
      var $empty = h();

      if (this.showEmpty && (!items || items.length === 0) && !(this.computedBusy && this.hasNormalizedSlot('table-busy'))) {
        var isFiltered = this.isFiltered,
            emptyText = this.emptyText,
            emptyHtml = this.emptyHtml,
            emptyFilteredText = this.emptyFilteredText,
            emptyFilteredHtml = this.emptyFilteredHtml,
            computedFields = this.computedFields,
            tbodyTrClass = this.tbodyTrClass,
            tbodyTrAttr = this.tbodyTrAttr;
        $empty = this.normalizeSlot(this.isFiltered ? 'emptyfiltered' : 'empty', {
          emptyFilteredHtml: emptyFilteredHtml,
          emptyFilteredText: emptyFilteredText,
          emptyHtml: emptyHtml,
          emptyText: emptyText,
          fields: computedFields,
          // Not sure why this is included, as it will always be an empty array
          items: this.computedItems
        });

        if (!$empty) {
          $empty = h('div', {
            class: ['text-center', 'my-2'],
            domProps: isFiltered ? htmlOrText(emptyFilteredHtml, emptyFilteredText) : htmlOrText(emptyHtml, emptyText)
          });
        }

        $empty = h(BTd, {
          props: {
            colspan: computedFields.length || null
          }
        }, [h('div', {
          attrs: {
            role: 'alert',
            'aria-live': 'polite'
          }
        }, [$empty])]);
        $empty = h(BTr, {
          staticClass: 'b-table-empty-row',
          class: [isFunction(tbodyTrClass) ?
          /* istanbul ignore next */
          this.tbodyTrClass(null, 'row-empty') : tbodyTrClass],
          attrs: isFunction(tbodyTrAttr) ?
          /* istanbul ignore next */
          this.tbodyTrAttr(null, 'row-empty') : tbodyTrAttr,
          key: isFiltered ? 'b-empty-filtered-row' : 'b-empty-row'
        }, [$empty]);
      }

      return $empty;
    }
  }
};